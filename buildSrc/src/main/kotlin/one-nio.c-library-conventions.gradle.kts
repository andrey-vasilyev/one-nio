@file:Suppress("UnstableApiUsage")

plugins {
    `cpp-library` // there is no c-library in gradle atm, so we use cpp-library plugin and reconfigure it to support C
}

interface CLibExt {
    val moreCompilerArgs: ListProperty<String>
}

val cLibExt = project.extensions.create<CLibExt>("cLib")

library {
    baseName = "onenio-${project.name.substringBefore("-native")}"
    binaries.configureEach {
        if (toolChain !is GccCompatibleToolChain) {
            throw GradleException("Non-gcc tool chains aren't supported at the moment")
        }
        val compileTask = compileTask.get()
        compileTask.compilerArgs.addAll(
            "-x",
            "c",
            "-std=c11",
            "-D_GNU_SOURCE",
            "-fPIC",
            "-shared",
            "-O3",
            "-fno-omit-frame-pointer",
            "-momit-leaf-frame-pointer",
            "--verbose",
        )
        compileTask.compilerArgs.addAll(cLibExt.moreCompilerArgs)

        val javaHome = System.getProperty("java.home")
        compileTask.includes.from("$javaHome/include")

        val osFamily = targetPlatform.targetMachine.operatingSystemFamily
        if (osFamily.isMacOs) {
            compileTask.includes.from("$javaHome/include/darwin")
        } else if (osFamily.isLinux) {
            compileTask.includes.from("$javaHome/include/linux")
        } else if (osFamily.isWindows) {
            compileTask.includes.from("$javaHome/include/win32")
        }

        compileTask.source.from(project.fileTree("src/main/c") { include("*.c") })
    }
    linkage = setOf(Linkage.SHARED)
}


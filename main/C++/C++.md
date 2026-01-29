┌─────────────────────────────────────────────────────────┐
│                    Your Project                          │
│                  CMakeLists.txt                          │
│                  (Build instructions)                    │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
              ┌──────────────┐
              │    CMake     │ ◄── Meta-build system
              │  (Generator) │
              └──────┬───────┘
                     │
        ┌────────────┴────────────┐
        │                         │
        ▼                         ▼
┌───────────────┐         ┌──────────────┐
│   Windows     │         │     Mac      │
│               │         │              │
│ Visual Studio │         │   Xcode      │
│  .sln files   │         │  project or  │
│      or       │         │  Makefiles   │
│ Ninja files   │         │              │
└───────┬───────┘         └──────┬───────┘
        │                        │
        ▼                        ▼
┌───────────────┐         ┌──────────────┐
│  MSVC (cl.exe)│         │Clang/GCC     │ ◄── Actual compilers
│   Compiler    │         │  Compiler    │
└───────┬───────┘         └──────┬───────┘
        │                        │
        └────────────┬───────────┘
                     │
                     ▼
              ┌──────────────┐
              │  Executable  │
              │   (.exe/.app)│
              └──────────────┘

{
    // CMake settings
    "cmake.configureOnOpen": true,
    "cmake.buildDirectory": "${workspaceFolder}/build",
    "cmake.generator": "Ninja",
    "cmake.configureSettings": {
        "CMAKE_EXPORT_COMPILE_COMMANDS": "ON"
    },
    
    // C++ IntelliSense
    "C_Cpp.default.configurationProvider": "ms-vscode.cmake-tools",
    "C_Cpp.intelliSenseEngine": "default",
    
    // Editor settings
    "files.associations": {
        "*.h": "cpp",
        "*.hpp": "cpp",
        "*.cpp": "cpp",
        "CMakeLists.txt": "cmake"
    },
    
    // Line endings (important for cross-platform)
    "files.eol": "\n",
    "files.insertFinalNewline": true,
    "files.trimTrailingWhitespace": true
}
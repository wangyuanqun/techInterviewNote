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

# Configuration

## CMake Setup with VSCode and MSVC

### Prerequisites
- VSCode installed
- C/C++ extension installed
- CMake Tools extension installed
- Visual Studio 2026 with MSVC installed (Windows)
- Xcode Command Line Tools (Mac)

---

## Step 1: Install CMake

### On Windows

**Option A: Using Visual Studio Installer (Recommended)**
1. Open **Visual Studio Installer**
2. Click **Modify** on your Visual Studio 2026 installation
3. Go to **Individual components** tab
4. Search for "CMake"
5. Check C++ CMake tools for Windows
6. Click **Modify** to install

**Option B: Using Chocolatey**
```powershell
# Run PowerShell as Administrator
choco install cmake
```

**Option C: Direct Download**
1. Download from: https://cmake.org/download/
2. Get the Windows installer (`.msi`)
3. Run installer and **check "Add CMake to system PATH"**

**Verify Installation:**
```cmd
cmake --version
```

### On Mac

```bash
# Install Xcode Command Line Tools (if not already)
xcode-select --install

# Install Homebrew (if not already)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install CMake
brew install cmake

# Verify
cmake --version
```

---

## Step 2: Install Ninja Build System (Optional but Recommended)

Ninja is faster than MSBuild and works on both Windows and Mac.

**Windows:**
```powershell
choco install ninja
```

**Mac:**
```bash
brew install ninja
```

**Verify:**
```bash
ninja --version
```

---

## Step 3: Configure VSCode Settings

### User Settings (Global)

Press `Ctrl+Shift+P` (Windows) or `Cmd+Shift+P` (Mac), then type:
**"Preferences: Open User Settings (JSON)"**

Add these settings:

```json
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
```

---

## Step 4: Project Structure

Recommended structure for cross-platform C++ projects:

```
your-project/
├── .vscode/
│   ├── settings.json          # Workspace settings
│   ├── c_cpp_properties.json  # C++ IntelliSense config
│   ├── tasks.json             # Build tasks
│   └── launch.json            # Debug configurations
├── src/
│   └── main.cpp
├── include/
│   └── *.h
├── CMakeLists.txt             # Cross-platform build config
├── .gitignore
└── README.md
```

---

## Step 5: Configuration Files

### `.vscode/settings.json` (Workspace)

```json
{
    "cmake.configureOnOpen": true,
    "cmake.buildDirectory": "${workspaceFolder}/build",
    "C_Cpp.default.configurationProvider": "ms-vscode.cmake-tools"
}
```

### `.vscode/c_cpp_properties.json`

```json
{
    "configurations": [
        {
            "name": "Mac",
            "includePath": [
                "${workspaceFolder}/**",
                "${workspaceFolder}/include"
            ],
            "defines": [],
            "macFrameworkPath": [
                "/Library/Developer/CommandLineTools/SDKs/MacOSX.sdk/System/Library/Frameworks"
            ],
            "compilerPath": "/usr/bin/clang++",
            "cStandard": "c17",
            "cppStandard": "c++17",
            "intelliSenseMode": "macos-clang-arm64",
            "configurationProvider": "ms-vscode.cmake-tools"
        },
        {
            "name": "Win32",
            "includePath": [
                "${workspaceFolder}/**",
                "${workspaceFolder}/include"
            ],
            "defines": [
                "_DEBUG",
                "UNICODE",
                "_UNICODE"
            ],
            "windowsSdkVersion": "10.0.22000.0",
            "compilerPath": "C:/Program Files/Microsoft Visual Studio/2022/Community/VC/Tools/MSVC/14.30.30705/bin/Hostx64/x64/cl.exe",
            "cStandard": "c17",
            "cppStandard": "c++17",
            "intelliSenseMode": "windows-msvc-x64",
            "configurationProvider": "ms-vscode.cmake-tools"
        }
    ],
    "version": 4
}
```

### `.vscode/tasks.json`

```json
{
    "version": "2.0.0",
    "tasks": [
        {
            "label": "CMake: Configure",
            "type": "shell",
            "command": "cmake",
            "args": [
                "-B",
                "${workspaceFolder}/build",
                "-S",
                "${workspaceFolder}"
            ],
            "group": "build",
            "problemMatcher": []
        },
        {
            "label": "CMake: Build",
            "type": "shell",
            "command": "cmake",
            "args": [
                "--build",
                "${workspaceFolder}/build",
                "--config",
                "Debug"
            ],
            "group": {
                "kind": "build",
                "isDefault": true
            },
            "problemMatcher": ["$gcc"],
            "dependsOn": ["CMake: Configure"]
        },
        {
            "label": "CMake: Clean",
            "type": "shell",
            "command": "cmake",
            "args": [
                "--build",
                "${workspaceFolder}/build",
                "--target",
                "clean"
            ],
            "group": "build",
            "problemMatcher": []
        }
    ]
}
```

### `.vscode/launch.json`

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "name": "(lldb) Launch",
            "type": "cppdbg",
            "request": "launch",
            "program": "${workspaceFolder}/build/${workspaceFolderBasename}",
            "args": [],
            "stopAtEntry": false,
            "cwd": "${workspaceFolder}",
            "environment": [],
            "externalConsole": false,
            "MIMode": "lldb",
            "preLaunchTask": "CMake: Build",
            "osx": {
                "MIMode": "lldb"
            }
        },
        {
            "name": "(msvc) Launch",
            "type": "cppvsdbg",
            "request": "launch",
            "program": "${workspaceFolder}/build/Debug/${workspaceFolderBasename}.exe",
            "args": [],
            "stopAtEntry": false,
            "cwd": "${workspaceFolder}",
            "environment": [],
            "console": "integratedTerminal",
            "preLaunchTask": "CMake: Build",
            "windows": {
                "type": "cppvsdbg"
            }
        }
    ]
}
```

### `CMakeLists.txt`

```cmake
cmake_minimum_required(VERSION 3.15)
project(MyProject VERSION 1.0.0 LANGUAGES CXX)

# Set C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

# Platform-specific settings
if(WIN32)
    add_compile_definitions(PLATFORM_WINDOWS)
    # MSVC-specific flags
    if(MSVC)
        add_compile_options(/W4 /WX)
    endif()
elseif(APPLE)
    add_compile_definitions(PLATFORM_MAC)
    # Clang-specific flags
    add_compile_options(-Wall -Wextra -Wpedantic)
endif()

# Include directories
include_directories(${PROJECT_SOURCE_DIR}/include)

# Source files
file(GLOB_RECURSE SOURCES "src/*.cpp")
file(GLOB_RECURSE HEADERS "include/*.h")

# Create executable
add_executable(${PROJECT_NAME} ${SOURCES} ${HEADERS})

# Platform-specific linking
if(WIN32)
    # Windows-specific libraries
elseif(APPLE)
    # Mac-specific frameworks
    target_link_libraries(${PROJECT_NAME} "-framework CoreFoundation")
endif()
```

### `.gitignore`

```gitignore
# Build directories
build/
cmake-build-*/
out/

# VSCode
.vscode/ipch/
.vscode/.cmaketools.json

# OS-specific
.DS_Store
Thumbs.db

# Compiled files
*.o
*.obj
*.exe
*.out
*.app
```

---

## Step 6: Using CMake in VSCode

### First Time Setup

1. **Open project folder** in VSCode
2. **Select a Kit** (first time only):
   - Press `Ctrl+Shift+P` / `Cmd+Shift+P`
   - Type: **"CMake: Select a Kit"**
   - **Windows:** Choose "Visual Studio Community 2026 Release - amd64"
   - **Mac:** Choose "Clang" (from Xcode)

### Build Workflow

| Action | Keyboard Shortcut | Command Palette |
|--------|------------------|-----------------|
| Configure | - | `CMake: Configure` |
| Build | `F7` or `Ctrl+Shift+B` | `CMake: Build` |
| Run | `Shift+F5` | `CMake: Run Without Debugging` |
| Debug | `Ctrl+F5` | `CMake: Debug` |
| Clean | - | `CMake: Clean` |
| Select Kit | - | `CMake: Select a Kit` |

### VSCode Status Bar

After setup, you'll see these CMake controls:

```
[Kit: Visual Studio 2026] [Debug] [Build] [▶️] [🐛]
```

- **Kit:** Compiler toolchain
- **Debug/Release:** Build configuration
- **Build:** Build button
- **▶️:** Run without debugging
- **🐛:** Debug

---

## Step 7: Command Line Usage

### On Windows

```cmd
# Configure
cmake -B build -G "Visual Studio 17 2026"

# Build
cmake --build build --config Release

# Run
.\build\Release\MyProject.exe
```

### On Mac

```bash
# Configure
cmake -B build

# Build
cmake --build build

# Run
./build/MyProject
```

---

## Troubleshooting

### Issue: CMake doesn't find MSVC

**Solution 1:** Open "Developer Command Prompt for VS 2026", then run:
```cmd
code .
```

**Solution 2:** Run VS environment setup:
```cmd
"C:\Program Files\Microsoft Visual Studio\2026\Community\Common7\Tools\VsDevCmd.bat"
```

### Issue: "No CMake kits available"

**Solution:**
1. Press `Ctrl+Shift+P`
2. Type: **"CMake: Scan for Kits"**
3. Select your Visual Studio kit

### Issue: Build fails with "ninja: command not found"

**Solution:** Either install Ninja, or change generator in settings:
```json
"cmake.generator": "Visual Studio 17 2026"  // Windows
// or
"cmake.generator": "Unix Makefiles"  // Mac
```

### Issue: IntelliSense not working

**Solution:**
1. Ensure `"C_Cpp.default.configurationProvider": "ms-vscode.cmake-tools"` is set
2. Run `CMake: Configure` to generate compile commands
3. Reload VSCode window

---

## Example Project

### `src/main.cpp`

```cpp
#include <iostream>

int main() {
    std::cout << "Hello from CMake!" << std::endl;

    #ifdef PLATFORM_WINDOWS
        std::cout << "Running on Windows with MSVC" << std::endl;
    #elif defined(PLATFORM_MAC)
        std::cout << "Running on macOS with Clang" << std::endl;
    #endif

    return 0;
}
```

### Build and Run

1. Open project in VSCode
2. Press `F7` to build
3. Press `Shift+F5` to run

**Expected Output:**
```
Hello from CMake!
Running on Windows with MSVC
```

---

## Best Practices

1. **Use CMake for cross-platform projects** - Write once, build anywhere
2. **Commit `.vscode/` folder** - Share configuration with team
3. **Use Ninja** - Faster builds than MSBuild/Make
4. **Set C++ standard in CMakeLists.txt** - Ensures consistency
5. **Use platform detection** - Handle platform-specific code gracefully
6. **Enable compile commands** - Better IntelliSense support
7. **Use consistent line endings** - Set `files.eol` to `\n`
8. **Test on both platforms** - Use CI/CD (GitHub Actions, etc.)


# Debug error
 *  Executing task: cmake -B D:\GitRepo\SoundTrack/build -S D:\GitRepo\SoundTrack

cmake : The term 'cmake' is not recognized as the name of a cmdlet, function, script file, or operable program. Check the spelling of the name, or if a path was
included, verify that the path is correct and try again.
At line:1 char:1
+ cmake -B D:\GitRepo\SoundTrack/build -S D:\GitRepo\SoundTrack
+ ~~~~~
    + CategoryInfo          : ObjectNotFound: (cmake:String) [], CommandNotFoundException
    + FullyQualifiedErrorId : CommandNotFoundException


 *  The terminal process "C:\WINDOWS\System32\WindowsPowerShell\v1.0\powershell.exe -Command cmake -B D:\GitRepo\SoundTrack/build -S D:\GitRepo\SoundTrack" terminated with exit code: 1.
 *  Terminal will be reused by tasks, press any key to close it.

cmake build (cmake: select a kit):
// 1 amd64
[main] Building folder: d:/GitRepo/SoundTrack/build
[build] Starting build
[proc] Executing command: "C:\Program Files (x86)\Microsoft Visual Studio\18\BuildTools\Common7\IDE\CommonExtensions\Microsoft\CMake\CMake\bin\cmake.exe" --build d:/GitRepo/SoundTrack/build --config Debug --target all --
[build] ninja: no work to do.
[driver] Build completed: 00:00:00.078
[build] Build finished with exit code 0

// 2 amd64_x86
[driver] Switching to kit: Visual Studio Build Tools 2026 Release - amd64_x86
[main] Configuring project: SoundTrack
[proc] Executing command: "C:\Program Files (x86)\Microsoft Visual Studio\18\BuildTools\Common7\IDE\CommonExtensions\Microsoft\CMake\CMake\bin\cmake.exe" -DCMAKE_BUILD_TYPE:STRING=Debug -DCMAKE_EXPORT_COMPILE_COMMANDS:BOOL=TRUE --no-warn-unused-cli -S D:/GitRepo/SoundTrack -B d:/GitRepo/SoundTrack/build -G Ninja
[cmake] Not searching for unused variables given on the command line.
[cmake] -- Configuring done (0.1s)
[cmake] -- Generating done (0.1s)
[cmake] -- Build files have been written to: D:/GitRepo/SoundTrack/build


//3 x86
[driver] Switching to kit: Visual Studio Build Tools 2026 Release - x86
[driver] Removing d:/GitRepo/SoundTrack/build/CMakeCache.txt
[driver] Removing d:%5CGitRepo%5CSoundTrack%5Cbuild%5CCMakeFiles
[main] Configuring project: SoundTrack
[proc] Executing command: "C:\Program Files (x86)\Microsoft Visual Studio\18\BuildTools\Common7\IDE\CommonExtensions\Microsoft\CMake\CMake\bin\cmake.exe" -DCMAKE_BUILD_TYPE:STRING=Debug -DCMAKE_EXPORT_COMPILE_COMMANDS:BOOL=TRUE --no-warn-unused-cli -S D:/GitRepo/SoundTrack -B d:/GitRepo/SoundTrack/build -G Ninja
[cmake] Not searching for unused variables given on the command line.
[cmake] -- The CXX compiler identification is MSVC 19.50.35723.0
[cmake] -- Detecting CXX compiler ABI info
[cmake] -- Detecting CXX compiler ABI info - done
[cmake] -- Check for working CXX compiler: C:/Program Files (x86)/Microsoft Visual Studio/18/BuildTools/VC/Tools/MSVC/14.50.35717/bin/Hostx86/x86/cl.exe - skipped
[cmake] -- Detecting CXX compile features
[cmake] -- Detecting CXX compile features - done
[cmake] -- Configuring done (2.3s)
[cmake] -- Generating done (0.1s)
[cmake] -- Build files have been written to: D:/GitRepo/SoundTrack/build


//4 x86_amd64
[driver] Switching to kit: Visual Studio Build Tools 2026 Release - x86_amd64
[main] Configuring project: SoundTrack
[proc] Executing command: "C:\Program Files (x86)\Microsoft Visual Studio\18\BuildTools\Common7\IDE\CommonExtensions\Microsoft\CMake\CMake\bin\cmake.exe" -DCMAKE_BUILD_TYPE:STRING=Debug -DCMAKE_EXPORT_COMPILE_COMMANDS:BOOL=TRUE --no-warn-unused-cli -S D:/GitRepo/SoundTrack -B d:/GitRepo/SoundTrack/build -G Ninja
[cmake] Not searching for unused variables given on the command line.
[cmake] -- Configuring done (0.0s)
[cmake] -- Generating done (0.1s)
[cmake] -- Build files have been written to: D:/GitRepo/SoundTrack/build

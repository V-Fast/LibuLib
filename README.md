
<center><div align="center">

# LibuLib

![Fabric only](https://raw.githubusercontent.com/u-lumaa/u-lumaa/main/assets/fabric-banner.png)  
[![JitPack](https://jitpack.io/v/u-lumaa/LibuLib.svg)](https://jitpack.io/#u-lumaa/LibuLib)

</div></center>
**LibuLib** is a library mod made for all the mods by myself

**The wiki is coming.** You can look at [The Backrooms Mod's GitHub](https://github.com/u-lumaa/BackroomsMod) to find out how to use **LibuLib** in your mod.  
You can also use it in your mod by doing the following (JitPack):

**gradle.properties**:
```properties
# Put the latest version of LibuLib
libu_version = 1.0.0
```

**build.gradle**:
```gradle
repositories {
	maven {
		url "https://jitpack.io"
	}
}

dependencies {
	// LibuLib
	modImplementation "com.github.u-lumaa:LibuLib:${libu_version}"
}
```
* * *

You can use it in your mod by doing the following (Modrinth Maven):

**gradle.properties**:
```properties
# Put the latest version of LibuLib
libu_version = 1.0.0
```

**build.gradle**:
```gradle
repositories {
    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth"
                url = "https://api.modrinth.com/maven"
            }
        }
        filter {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    modImplementation "maven.modrinth:libu:${libu_version}"
}
```

Use developer versions at your own risk

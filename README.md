<center><div align="center">

# LibuLib

![Fabric only](https://raw.githubusercontent.com/u-lumaa/u-lumaa/main/assets/fabric-banner.png)  
[![JitPack](https://jitpack.io/v/u-lumaa/LibuLib.svg)](https://jitpack.io/#u-lumaa/LibuLib)
![Modrinth Downloads](https://img.shields.io/modrinth/dt/libu?label=Modrinth&logo=modrinth)
![Modrinth Game Versions](https://img.shields.io/modrinth/game-versions/libu?label=Minecraft%20Version)  
[![Discord Server](https://img.shields.io/discord/1033451342984908900?label=Support%20Discord&logo=discord)](https://discord.gg/Rqpn3C7yR5)

</div></center>

**LibuLib** is a library mod made for all the mods by myself  
Wiki: https://github.com/u-lumaa/LibuLib/wiki

Use it in your mod by doing the following (JitPack):

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

**Use developer versions at your own risk**
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

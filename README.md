# LibuLib

[![Fabric](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_64h.png)](https://fabricmc.net)
[![Requires Fabric API](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/requires/fabric-api_64h.png)](https://modrinth.com/mod/fabric-api)
[![GitBook](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/documentation/gitbook_64h.png)](https://lumaa.gitbook.io/libulib)
[![Support Discord](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/social/discord-singular_64h.png)](https://discord.gg/Rqpn3C7yR5)  
![Gradle](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/built-with/gradle_46h.png)
[![JitPack](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/available/jitpack_46h.png)](https://jitpack.io/#lumaa-dev/LibuLib)

**LibuLib** is a library mod made for all the mods by myself  

Use it in your mod by doing the following (JitPack):

**gradle.properties**:
```properties
# Put the latest version of LibuLib
libu_version = 1.2.1
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
libu_version = 1.2.1+1
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

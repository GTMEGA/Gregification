plugins {
    id("fpgradle-minecraft") version ("0.10.1")
}

group = "mega"

minecraft_fp {
    mod {
        modid = "gregification"
        name = "Gregification"
        rootPkg = "$group.gregification"
    }
    tokens {
        tokenClass = "Tags"
    }

    publish {
        maven {
            repoUrl = "https://mvn.falsepattern.com/gtmega_releases/"
            repoName = "mega"
        }
    }
}

repositories {
    ic2EX()
    exclusive(mega(), "gtmega", "mega", "codechicken")
    exclusive(maven("horizon", "https://mvn.falsepattern.com/horizon"), "com.github.GTNewHorizons")
    exclusive(maven("usrv", "https://mvn.falsepattern.com/usrv"), "eu.usrv")
    exclusive(maven("overmind", "https://mvn.falsepattern.com/overmind"), "com.azanor.baubles")
}

dependencies {
    implementation("gtmega:gt5u-mc1.7.10:5.43.4-mega:dev")
    implementation("mega:crafttweaker-mc1.7.10:3.5.0:dev")
}

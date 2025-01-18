# RunCfg Kotlin Client

### Usage in projects

First add the repo to your project dependencies
```kotlin
dependencies {
    implementation("com.runcfg.runcfg-kt:1.0.0")
}
```

Then import into your project

```kotlin
import org.runcfg.*
```

### Using your first config

1. Create an account at https://runcfg.com
2. Download your `.runcfg` file from your project page at https://runcfg.com by clicking (get .runcfg file)

![runcfg.PNG](https://raw.githubusercontent.com/runcfg/runcfg-net/main/runcfg.png)

3. Place your `.runcfg` file at the root of your project
4. Create your config type

```kotlin
@Serializable
data class Configuration(val version: String, val target: String, val enabled: String)
```

4. Load your remote config into your config type

```kotlin
import org.runcfg.*
import kotlinx.serialization.Serializable

@Serializable
data class Configuration(val version: String, val target: String, val enabled: String)

fun main() {
    val client = LoadConfigAsType<Configuration>()
    println(client.target)
}
```

You can now access your configuration from the
config type `<T>` passed into the `LoadConfigAsType<T>` function for example:
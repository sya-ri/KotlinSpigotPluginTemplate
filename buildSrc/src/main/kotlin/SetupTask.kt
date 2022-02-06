import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.IOException

/**
 * GitHub アカウントを使って プロジェクトをセットアップする
 */
open class SetupTask : DefaultTask() {
    @TaskAction
    fun action() {
        val projectDir = project.projectDir
        val repository = try {
            FileRepositoryBuilder.create(projectDir.resolve(".git"))
        } catch (ex: IOException) {
            error("リポジトリが見つかりませんでした")
        }
        val git = Git(repository)
        val remoteList = git.remoteList().call()
        val uri = remoteList.flatMap { it.urIs }.firstOrNull { it.host == "github.com" } ?: error("GitHub のプッシュ先が見つかりませんでした")
        val rawAccount = "/?([^/]*)/?".toRegex().find(uri.path)?.groupValues?.get(1) ?: error("アカウント名が見つかりませんでした (${uri.path})")
        val account = rawAccount.replace('-', '_')
        val groupId = "com.github.$account"
        val srcDir = projectDir.resolve("src/main/kotlin/com/github/$account").apply(File::mkdirs)
        srcDir.resolve("Main.kt").writeText(
            """
                package $groupId

                import org.bukkit.plugin.java.JavaPlugin

                class Main : JavaPlugin()
                
            """.trimIndent()
        )
        val buildScript = projectDir.resolve("build.gradle.kts")
        buildScript.writeText(buildScript.readText().replace("@group@", groupId))
        projectDir.resolve("README.md").writeText(
            """
                # ${project.name}

                ## plugin.yml

                ビルド時に自動生成されます。[build.gradle.kts](build.gradle.kts) の以下の箇所で設定できます。
                書き方は https://github.com/Minecrell/plugin-yml の Bukkit kotlin-dsl を見てください。

                ```kotlin
                configure<BukkitPluginDescription> {
                    // 内容
                }
                ```

                ## タスク

                ### プラグインのビルド `build`

                `build/libs` フォルダに `.jar` を生成します。

                ### テストサーバーの起動 `buildAndLaunchServer`

                `:25565` でテストサーバーを起動します。

            """.trimIndent()
        )
    }
}

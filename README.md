# SpigotPluginTemplate

## TODO
変更が必要なところがあります。

パッケージ名を設定しましょう。 ドメインを逆さにするのが定石になります。もしも自分のドメインを持っていない時は GitHub のアカウントを使いましょう。

### [Main.kt](src/main/java/sample/Main.java)

```java
package sample; // TODO 固有のパッケージ名を設定する
```

> e.g. GitHub のユーザー名が `abcdef` で、作るプラグインの名前が `Sample` だったら
>
> ```java
> package com.github.abcdef.sample;
> ```

### [build.gradle.kts](build.gradle.kts)

```kotlin
configure<BukkitPluginDescription> {
    // ...
    main = "sample.Main" // TODO JavaPlugin を継承したクラスとパッケージを入力する
    // ...
}
```
> e.g. GitHub のユーザー名が `abcdef` で、作るプラグインの名前が `Sample` だったら
>
> ```kotlin
> configure<BukkitPluginDescription> {
>     // ...
>     main = "com.github.abcdef.sample.Main"
>     // ...
> }
> ```

### plugin.yml

[build.gradle.kts](build.gradle.kts) の中で設定できます。必要であれば設定しましょう。

```kotlin
configure<BukkitPluginDescription> {
    // plugin.yml の中身
}
```

## タスク

### プラグインのビルド `build`

`build/libs` フォルダに `.jar` を生成します。

### テストサーバーの起動 `buildAndLaunchServer`

`:25565` でテストサーバーを起動します。

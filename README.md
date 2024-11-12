# Wikitori

Wikitoriは、しりとりゲームをプレイするためのJavaベースのWebアプリケーションです。このアプリケーションは、ユーザーが入力した単語に対して、ボットがしりとりのルールに従って応答します。

## 機能

- ユーザーが入力した単語に対して、ボットがしりとりのルールに従って応答
- チャット履歴の保存と表示
- 重複単語のチェック

## セットアップ

### 前提条件

- Java 8以上
- Apache Maven
- Tomcat 9以上

### インストール手順

1. リポジトリをクローンします。

    ```sh
    git clone https://github.com/yourusername/wikitori.git
    cd wikitori
    ```

2. 必要な依存関係をインストールします。

    ```sh
    mvn clean install
    ```

3. Tomcatサーバーにプロジェクトをデプロイします。

    ```sh
    mvn tomcat7:deploy
    ```

4. ブラウザでアプリケーションにアクセスします。

    ```
    http://localhost:8080/wikitori/chat
    ```

## 使用方法

1. チャット画面にアクセスします。
2. テキストボックスに単語を入力し、送信ボタンをクリックします。
3. ボットがしりとりのルールに従って応答します。

## プロジェクト構成

- `src/main/java/servlet/ChatController.java`: チャット画面のコントローラー
- `src/main/java/model/ShiritoriModel.java`: しりとりのロジックを処理するモデル
- `src/main/java/model/ChatHistory.java`: チャット履歴を管理するモデル
- `src/main/java/model/TitleInfo.java`: しりとりの単語情報を管理するモデル
- `src/main/java/util/StringUtil.java`: 入力検証のユーティリティクラス

## 貢献

貢献を歓迎します。プルリクエストを送る前に、まずIssueを立ててください。

## ライセンス

このプロジェクトはMITライセンスの下で公開されています。詳細については、`LICENSE`ファイルを参照してください。

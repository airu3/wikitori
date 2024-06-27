# buildステージを開始、Eclipse Temurin 17 JDKを適応したMavenということを設定
FROM maven:3-eclipse-temurin-17 AS build
# 現在のディレクトリをコンテナの中の/appディレクトリにコピー
COPY . .
# アプリケーションの実行と、テストのスキップ。
RUN mvn clean package -Dmaven.test.skip=true

# 新しいビルドステージの開始
FROM eclipse-temurin:17-alpine
# 前のビルドステージからビルドされたWARファイル(shiritori-0.0.1-SNAPSHOT.war)をコピー
COPY --from=build /target/shiritori-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/shiritori.war

# ポート8080を公開
EXPOSE 8080

# Tomcatを起動
CMD ["catalina.sh", "run"]

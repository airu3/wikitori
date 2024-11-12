# Mavenをベースにしたビルドステージ
FROM maven:3-eclipse-temurin-17 AS build
# ワーキングディレクトリの設定
COPY . .
# ビルド
RUN mvn clean package -Dmaven.test.skip=true

# Tomcatのインストール
FROM tomcat:9-jdk17-openjdk-slim
# ビルドステージからビルド成果物をコピー
COPY --from=build /target/wikitori-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/wikitori.war
# ポートのエクスポート
EXPOSE 8080
# Tomcatの起動
CMD ["catalina.sh", "run"]
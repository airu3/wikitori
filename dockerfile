# ベースイメージとしてEclipse Temurin 17 JDKを使用
FROM eclipse-temurin:17-alpine
# WARファイルをコンテナの/usr/local/tomcat/webapps/ディレクトリにコピー
COPY target/shiritori-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/shiritori.war
# ポート8080を公開
EXPOSE 8080
# Tomcatを起動
CMD ["catalina.sh", "run"]
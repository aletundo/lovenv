FROM bde2020/spark-worker:2.3.1-hadoop2.7

RUN apt-get update && apt-get install -y debian-keyring debian-archive-keyring

RUN echo "deb http://ftp.de.debian.org/debian testing main" | tee -a /etc/apt/sources.list
RUN echo 'APT::Default-Release "jessie";' | tee -a /etc/apt/apt.conf.d/00local

RUN apt-get update && apt-get -y -t testing install python3.6

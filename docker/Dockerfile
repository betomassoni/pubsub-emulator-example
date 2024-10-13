# Usar a imagem do Google Cloud SDK com emuladores
FROM google/cloud-sdk:emulators

COPY ./emulators /emulators


RUN chmod +x /emulators
RUN apt-get update && apt-get install -y git
RUN apt-get update && apt-get install -y python3 python3-pip && \
    pip3 install google-cloud-pubsub

# Definir a variável de ambiente
ENV PUBSUB_PROJECT_ID=some-project

# Comando de entrada
# ENTRYPOINT ["sh", "-c", "gcloud beta emulators pubsub start --host-port=0.0.0.0:8085 --project=some-project2"]
ENTRYPOINT ["sh", "-c", "gcloud beta emulators pubsub start --host-port=0.0.0.0:8085 & sleep 5 && /emulators/pubsub/init-pubsub.sh"]

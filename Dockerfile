FROM python:latest

RUN apt-get update \
    && apt-get install curl -y \
    && curl -sL https://deb.nodesource.com/setup_16.x | bash \
    && apt-get install nodejs -y \
    && node -v \
    && npm -v 

COPY requirements.txt ./
RUN pip3 install -r requirements.txt --no-cache-dir

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install --only=production

COPY . ./

CMD [ "npm", "start" ]

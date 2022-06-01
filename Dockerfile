FROM nikolaik/python-nodejs:python3.10-nodejs17

WORKDIR /app
COPY package*.json /app
COPY requirements.txt /app

RUN pip install -r requirements.txt && npm install

COPY . /opt/app

CMD node server.js
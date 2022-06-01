FROM nikolaik/python-nodejs:python3.10-nodejs17

RUN mkdir /app
WORKDIR /app
COPY package*.json ./
COPY requirements.txt ./

RUN pip install -r requirements.txt
RUN npm install

COPY . /app

CMD ["npm", "start"]
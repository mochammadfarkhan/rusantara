FROM node:17.6

WORKDIR /app

COPY package*.json .
RUN npm install
COPY . .
CMD node index.js
FROM node:7
WORKDIR /app 
ENV NODE_ENV production

COPY package.json package.json

RUN npm install 

COPY . . 

EXPOSE 3000  

RUN npm install -g nodemon 

CMD [ "server.js" ] 


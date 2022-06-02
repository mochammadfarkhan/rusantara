const { initializeApp } = require('firebase/app');

const firebaseConfig = {
  apiKey: "AIzaSyBTwcsjoZOyTr2pLRjtNMTsvA2KJ6PrRQE",
  authDomain: "capstone-project-3a6c4.firebaseapp.com",
  databaseURL:
    "https://capstone-project-3a6c4-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "capstone-project-3a6c4",
  storageBucket: "capstone-project-3a6c4.appspot.com",
  messagingSenderId: "414977128484",
  appId: "1:414977128484:web:953a86f9949db2faaf80b1",
  measurementId: "G-T4G6M84PJ4",
};

const app = initializeApp(firebaseConfig);

module.exports = app;
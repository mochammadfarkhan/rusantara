let { PythonShell } = require('python-shell');
const express = require('express');
const app = express();

const path = require('path');

const multer  = require('multer')
const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, 'uploads')
  },
  filename: (req, file, cb) => {
    cb(null, Date.now() + '-' + path.extname(file.originalname))
  }
})

const upload = multer({ storage: storage }) 

app.get('/', (req, res) => {
  res.send('API for Rusantara app');
})

app.post('/predict', upload.single('image'), (req, res) => {
  const options = {
    args: req.file.filename
  };
  
  PythonShell.run('app.py', options, (err, results) => {
    res.send(results[0])
  });
})

app.listen(5000, () => {
  console.log('listening on port 5000');
});
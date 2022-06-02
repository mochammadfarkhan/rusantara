const admin = require('../config/firebase-admin');
const app = require('../config/firebase');
const { getAuth, signInWithEmailAndPassword, signOut } = require('firebase/auth');

const auth = getAuth(app);

exports.addUser = async (req, res) => {
  const { username, email, password } = req.body;
  try {
    const user = await admin.auth().createUser({
      displayName: username, 
      email, 
      password
    });
    res.json(user);
  } catch (error) {
    res.status(401).json({ error: error.message });
  }
}

exports.authenticate = async (req, res) => {
  const { email, password } = req.body;
  try {
    const user = await signInWithEmailAndPassword(auth, email, password);
    const currentUser = {
      uid: user.user.uid,
      username: user.user.displayName,
      email: user.user.email,
      refreshToken: user.user.refreshToken,
      idtoken: user.user.accessToken
    };
    res.json(currentUser);
  } catch (err) {
    res.status(401).json({ error: err.message });
  }
}

// exports.getToken = async (req, res) => {
//   const { uid } = req.body;

//   try {
//     const token = await admin.auth().createCustomToken(uid);
//     res.send(token);
//   } catch (err) {
//     res.status(401).json({ error: err.message });
//   }
// }

// exports.signOut = async (req, res) => {
//   try{
//     await signOut(auth, () => {
//       res.status(200).json({ message: "Signed Out" });
//     })
//   } catch (err) {
//     res.status(401).json({ error: err.message });
//   }
// }
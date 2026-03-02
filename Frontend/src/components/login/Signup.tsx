import { useState } from "react";
import "../../styles/App.css";
import Header from "../Header";
import type { User } from "../../constants/APIResponseTypes";

interface Props {
  user: User | null;
  setUser: (user: User | null) => void;
}

function Signup({ user, setUser }: Props) {
  const [password, setPassword] = useState("");
  const [passwordMatch, setPasswordMatch] = useState(true);

  async function newUser(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const form = event.currentTarget;
    const formData = new FormData(form);

    const password = formData.get("password") as string;
    const passwordConfirm = formData.get("password-confirm") as string;

    if (password !== passwordConfirm) {
      alert("Passwörter stimmen nicht überein!");
      return;
    }

    const user = {
      name: formData.get("username") as string,
      email: formData.get("email") as string,
      password: formData.get("password") as string,
    };

    const response = await fetch("http://localhost:8080/users", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
    });

    if (response.ok) {
      alert("Registrierung erfolgreich!");
      setUser(user);
    } else if (response.status === 409) {
      alert("Diese E-Mail-Adresse ist bereits registriert.");
    } else {
      alert("Fehler bei der Registrierung. Bitte versuche es erneut.");
    }
  }

  return (
    <>
      <Header user={user} />
      <div className="white-box">
        <h2>Registrieren</h2>
        <form onSubmit={newUser}>
          <div className="form-group">
            <label htmlFor="username">Benutzername:</label>
            <input type="text" id="username" name="username" required />
          </div>

          <div className="form-group">
            <label htmlFor="email">E-Mail:</label>
            <input type="email" id="email" name="email" required />
            <span className="error-message">
              Das sieht nicht nach einer Email-Adresse aus!
            </span>
          </div>

          <div className="form-group">
            <label htmlFor="password">Passwort:</label>
            <input
              type="password"
              id="password"
              name="password"
              onChange={(e) => setPassword(e.target.value)}
              required
              minLength={5}
            />
            <span className="error-message">
              Dein Passwort sollte mindestens 5 Zeichen haben!
            </span>
          </div>

          <div className="form-group">
            <label htmlFor="password-confirm">Passwort wiederholen:</label>
            <input
              type="password"
              id="password-confirm"
              name="password-confirm"
              onChange={(e) => {
                const value = (e.target as HTMLInputElement).value;
                setPasswordMatch(value === password);
                e.target.setCustomValidity(value === password ? "" : "invalid");
              }}
              required
            />
            {passwordMatch === false && (
              <span className="error-message">
                Die Passwörter stimmen nicht überein!
              </span>
            )}
          </div>

          <button type="submit">Registrieren</button>
        </form>
      </div>
    </>
  );
}

export default Signup;

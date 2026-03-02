import "../../styles/App.css";
import Header from "../Header";
import type { User } from "../../constants/APIResponseTypes";
import { useNavigate } from "react-router";

interface Props {
  user: User | null;
  setUser: (user: User | null) => void;
}

function Login({ user, setUser }: Props) {
  const Navigate = useNavigate();
  async function getUser(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    const form = event.currentTarget;
    const formData = new FormData(form);

    const user = {
      //   name: formData.get("username") as string,
      email: formData.get("email") as string,
      password: formData.get("password") as string,
    };

    const response = await fetch(
      `http://localhost:8080/users/einloggen?email=${user.email}&password=${user.password}`,
    );

    if (response.ok) {
      const user = (await response.json()) as User;
      setUser(user);
      Navigate("/hallenfinder");
    } else if (response.status === 401) {
      alert("Ungültige E-Mail oder Passwort.");
    } else {
      alert("Fehler bei der Anmeldung. Bitte versuche es erneut.");
    }
  }

  return (
    <>
      <Header user={user} />
      <div className="white-box">
        <h2>Anmelden</h2>
        <form onSubmit={getUser}>
          {/* <div className="form-group">
            <label htmlFor="username">Benutzername:</label>
            <input type="text" id="username" name="username" required />
          </div> */}

          <div className="form-group">
            <label htmlFor="email">E-Mail:</label>
            <input type="email" id="email" name="email" required />
            <span className="error-message">
              Das sieht nicht nach einer Email-Adresse aus!
            </span>
          </div>

          <div className="form-group">
            <label htmlFor="password">Passwort:</label>
            <input type="password" id="password" name="password" required />
          </div>

          <button type="submit">Anmelden</button>
        </form>
      </div>
    </>
  );
}

export default Login;

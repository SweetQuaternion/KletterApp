import { openDB } from "idb";

const getDB = async () => {
  return openDB("kletterapp-db", 1, {
    upgrade(db) {
      db.createObjectStore("hallen", { keyPath: "id" });
      db.createObjectStore("waende");
      db.createObjectStore("routen", { keyPath: "id" });
      db.createObjectStore("kommentare", { keyPath: "id" });
      db.createObjectStore("userRoutenStatus");
    },
  });
};

export default getDB;

import { openDB } from "idb";

const getDB = async () => {
  return openDB("kletterapp-db", 1, {
    upgrade(db) {
      db.createObjectStore("hallen", { keyPath: "id" });
      db.createObjectStore("waende");
      db.createObjectStore("routen", { keyPath: "id" });
      db.createObjectStore("kommentare", { keyPath: "id" });

      const userRoutenStatusStore = db.createObjectStore("userRoutenStatus");
      userRoutenStatusStore.createIndex("hallenId", "hallenId");

      const ascentStore = db.createObjectStore("ascents", { keyPath: "id", autoIncrement: true });
      ascentStore.createIndex("routenId", "routenId");
      ascentStore.createIndex("hallenId", "hallenId");

      db.createObjectStore("userRoutenStatusPending");
      db.createObjectStore("ascentsPending", { keyPath: "id", autoIncrement: true }).createIndex(
        "routenId",
        "routenId",
      );
    },
  });
};

export default getDB;

import { useContext, useState } from "react";
import { HalleContext, UserContext } from "../../constants/context";
import type { HalleResponseDTO } from "../../api/model";
import getDB from "../../constants/db";
import { useQuery } from "@tanstack/react-query";
import {
  createAllAscentsQueryOptions,
  createAllUserRoutenStatusQueryOptions,
  createWaendeByHallenIdQueryOptions,
} from "../../constants/queries";
import { useOnline } from "../../constants/useOnline";

const SpeicherKnopfsis = () => {
  const { selectedHalle } = useContext(HalleContext);
  const isOnline = useOnline();
  const [isHalleSync, setIsHalleSync] = useState(false);
  const user = useContext(UserContext);

  const { data: waende } = useQuery({
    ...createWaendeByHallenIdQueryOptions(selectedHalle!.id),
    enabled: isOnline,
  });

  const routenIdList =
    waende?.flatMap((wand) => wand.routen?.map((route) => route.id!) ?? []) ?? [];

  const { data: userRoutenStatusList } = useQuery(
    createAllUserRoutenStatusQueryOptions(user, routenIdList),
  );

  const { data: ascents } = useQuery(createAllAscentsQueryOptions(user, routenIdList));

  if (!selectedHalle) {
    return null;
  }

  const heimatHallen = JSON.parse(
    localStorage.getItem("Heimathallen") || "[]",
  ) as HalleResponseDTO[];

  const offlineHallen = JSON.parse(
    localStorage.getItem("OfflineHallen") || "[]",
  ) as HalleResponseDTO[];

  const [isHalleFavorit, setIsHalleFavorit] = useState(() => {
    return heimatHallen.some((halle) => halle.id === selectedHalle.id);
  });

  const [isHalleOffline, setIsHalleOffline] = useState(() => {
    return offlineHallen.some((halle) => halle.id === selectedHalle.id);
  });

  const handleFavoriteClick = () => {
    if (isHalleFavorit) {
      const updatedHallen = heimatHallen.filter((halle) => halle.id !== selectedHalle.id);
      localStorage.setItem("Heimathallen", JSON.stringify(updatedHallen));
    } else {
      heimatHallen.push(selectedHalle);
      localStorage.setItem("Heimathallen", JSON.stringify(heimatHallen));
    }
    setIsHalleFavorit(!isHalleFavorit);
  };

  const deleteHallenData = async () => {
    const db = await getDB();
    await db.delete("hallen", selectedHalle.id);
    await db.delete("waende", selectedHalle.id);
    const txAscents = db.transaction("ascents", "readwrite");
    const ascentKeys = await txAscents.store.index("hallenId").getAllKeys(selectedHalle.id);
    for (const key of ascentKeys) {
      await txAscents.store.delete(key);
    }
    await txAscents.done;

    const txStatus = db.transaction("userRoutenStatus", "readwrite");
    const statusKeys = await txStatus.store.index("hallenId").getAllKeys(selectedHalle.id);
    for (const key of statusKeys) {
      await txStatus.store.delete(key);
    }
    await txStatus.done;
  };

  const putHallenData = async () => {
    const db = await getDB();
    await db.put("hallen", selectedHalle);
    await db.put("waende", waende || [], selectedHalle.id);

    for (const status of userRoutenStatusList || []) {
      await db.put("userRoutenStatus", { ...status, hallenId: selectedHalle.id }, status.routenId);
    }
    for (const ascent of ascents || []) {
      await db.put("ascents", { ...ascent, hallenId: selectedHalle.id });
    }
  };

  const handleOfflineClick = async () => {
    if (isHalleOffline) {
      await deleteHallenData();
      const updatedHallen = offlineHallen.filter((halle) => halle.id !== selectedHalle.id);
      localStorage.setItem("OfflineHallen", JSON.stringify(updatedHallen));
      setIsHalleOffline(false);
      return;
    } else {
      await putHallenData();
      offlineHallen.push(selectedHalle);
      localStorage.setItem("OfflineHallen", JSON.stringify(offlineHallen));
      setIsHalleOffline(true);
    }
  };

  const handleSyncClick = async () => {
    if (isHalleSync) {
      return;
    }
    setIsHalleSync(true);
    await deleteHallenData();
    await putHallenData();

    setTimeout(() => {
      setIsHalleSync(false);
    }, 2000);
  };

  return (
    <div className="halle-favorit-container top right">
      <button
        className={`halle-favorit ${isHalleFavorit ? "active" : ""}`}
        title="Als Heimathalle markieren"
        aria-label="Als Heimathalle markieren"
        onClick={handleFavoriteClick}
      >
        ❤︎⁠
      </button>
      {isOnline && (
        <>
          <button
            className={`halle-favorit halle-offline ${isHalleOffline ? "active" : ""}`}
            title="Offline speichern"
            aria-label="Offline speichern"
            onClick={handleOfflineClick}
          >
            ↷
          </button>
          {isHalleOffline && (
            <>
              {isHalleSync ? (
                <button
                  className={"halle-favorit halle-offline sync active"}
                  title="in Sync"
                  aria-label="in Sync"
                  onClick={handleSyncClick}
                  disabled={isHalleSync}
                >
                  ✓
                </button>
              ) : (
                <button
                  className={"halle-favorit halle-offline sync"}
                  title="Syncronisieren"
                  aria-label="Syncronisieren"
                  onClick={handleSyncClick}
                  disabled={isHalleSync}
                >
                  ⟲
                </button>
              )}
            </>
          )}
        </>
      )}
    </div>
  );
};

export default SpeicherKnopfsis;

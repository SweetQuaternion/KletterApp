import "../../styles/News.css";
import { useQuery } from "@tanstack/react-query";
import { getGetNewsQueryOptions } from "../../api/news-controller/news-controller";
import { Link } from "react-router";
import { useState } from "react";

function KachelAllgemeineNews() {
  const { data: allgemeineNews } = useQuery(getGetNewsQueryOptions());
  const [showAllNews, setShowAllNews] = useState(false);

  return (
    <>
      <div className="white-box feed news">
        <div className="top-section">
          <h3>Allgemeine News</h3>
          {allgemeineNews && allgemeineNews.length != 0 ? (
            <>
              <p className="news-titel">{allgemeineNews?.[0]?.titel}</p>
              <p className="news-inhalt">{allgemeineNews?.[0]?.inhalt}</p>
              <div className="news-subline">
                <Link to={`/user/${allgemeineNews?.[0]?.autor}`}>
                  <p className="news-autor">{allgemeineNews?.[0]?.autor}</p>
                </Link>
                <p className="news-datum">
                  {new Date(allgemeineNews?.[0]?.datum || "").toLocaleDateString()}
                </p>
              </div>
            </>
          ) : (
            <p>Es gibt noch keine News.</p>
          )}
        </div>
        <button className="text-button" onClick={() => setShowAllNews(true)}>
          mehr ...
        </button>
        <img className="kachel-img" src="/images/news-kachel.webp" alt="Bild: News-Zettel"></img>
      </div>

      {showAllNews && (
        <div className="overlay news-overlay" onClick={() => setShowAllNews(false)}>
          <div className="white-box large news" onClick={(e) => e.stopPropagation()}>
            <button className="close-button">
              <button onClick={() => setShowAllNews(false)}>×</button>
            </button>
            <h2>Alle allgemeinen News</h2>

            <div className="news-container">
              {allgemeineNews?.map((news) => (
                <div className="news-item" key={news.id}>
                  <div className="flex-row news-headline">
                    <h3>{news.titel}</h3>
                    <div className="news-info">
                      <Link to={`/user/${news.autor}`}>
                        <p className="news-autor">{news.autor}</p>
                      </Link>
                      <p className="news-datum">
                        {new Date(news.datum || "").toLocaleDateString()}
                      </p>
                    </div>
                  </div>
                  <p className="news-inhalt">{news.inhalt}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
    </>
  );
}

export default KachelAllgemeineNews;

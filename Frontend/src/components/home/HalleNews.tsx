import "../../styles/News.css";
import { useQuery } from "@tanstack/react-query";
import { getGetNewsQueryOptions } from "../../api/news-controller/news-controller";
import { Link } from "react-router";
import { useState } from "react";
import type { HalleResponseDTO } from "../../api/model";

interface Props {
  halle: HalleResponseDTO | null;
}

function HalleNews({ halle }: Props) {
  const { data: halleNews } = useQuery(
    getGetNewsQueryOptions({ hallenId: halle?.id || undefined }),
  );
  const [showAllNews, setShowAllNews] = useState(false);

  if (!halle) {
    return (
      <div className="white-box feed news">
        <div>
          <h3>News zu deiner Heimathalle</h3>
          <p>Du hast noch keine Heimathalle markiert.</p>
        </div>
      </div>
    );
  }

  if (!halleNews || halleNews.length === 0) {
    return (
      <div className="white-box feed news">
        <div>
          <h3>News: {halle.name}</h3>
          <p>Es gibt noch keine News.</p>
        </div>
      </div>
    );
  }

  return (
    <>
      <div className="white-box feed news">
        <div className="top-section">
          <h3>News: {halle.name}</h3>
          <p className="news-titel">{halleNews?.[0]?.titel}</p>
          <p className="news-inhalt">{halleNews?.[0]?.inhalt}</p>
          <div className="news-subline">
            <Link to={`/user/${halleNews?.[0]?.autor}`}>
              <p className="news-autor">{halleNews?.[0]?.autor}</p>
            </Link>
            <p className="news-datum">
              {new Date(halleNews?.[0]?.datum || "").toLocaleDateString()}
            </p>
          </div>
        </div>
        <button className="text-button" onClick={() => setShowAllNews(true)}>
          mehr ...
        </button>
      </div>

      {showAllNews && (
        <div className="overlay news-overlay" onClick={() => setShowAllNews(false)}>
          <div className="white-box large news" onClick={(e) => e.stopPropagation()}>
            <h2>Alle News: {halle.name}</h2>

            <div className="news-container">
              {halleNews?.map((news) => (
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

export default HalleNews;

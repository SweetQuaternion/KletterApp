interface Props {
  scale: number;
  setScale: (scale: number) => void;
}

function Knopfsis({ scale, setScale }: Props) {
  return (
    <div className="knopfsis-container">
      <button onClick={() => setScale(scale - 0.1)}>-</button>
      <button onClick={() => setScale(scale + 0.1)}>+</button>
      <p>{(scale * 100).toFixed(0)}%</p>
    </div>
  );
}

export default Knopfsis;

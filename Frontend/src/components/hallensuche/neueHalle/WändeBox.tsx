import type { WandCreateDTO } from "../../../api/model";

interface Props {
  wände: WandCreateDTO[];
}

const WändeBox = ({ wände }: Props) => {
  return <div className="white-box">WändeBox</div>;
};

export default WändeBox;

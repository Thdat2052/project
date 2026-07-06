import parse from "html-react-parser";
import React from "react";

interface HtmlContentProps {
  content: string;
}

const HtmlContent: React.FC<HtmlContentProps> = ({ content }) => {
  return <>{parse(content)}</>;
};

export default HtmlContent;

import React, { useRef, useEffect } from "react";

export default function Footer() {
  const footerRef = useRef(null);

  useEffect(() => {
    const footerHeight = footerRef.current.getBoundingClientRect().height;
    console.log("Footer height:", footerHeight);
  }, []);

  return (
    <footer ref={footerRef} className="bg-gradient-to-t from-default-900 to-default bottom-0 w-full text-center p-default">
      <p className="text-sm">©2023 Swifticket, Inc. All rights reserved.</p>
    </footer>
  );
}

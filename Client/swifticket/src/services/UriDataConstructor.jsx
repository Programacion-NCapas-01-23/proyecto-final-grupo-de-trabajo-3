const uriDataConstructor = (dataObject) => {
  return Object.keys(dataObject)
    .map(
      (key) =>
        encodeURIComponent(key) + '=' + encodeURIComponent(dataObject[key])
    )
    .join('&');
};

export default uriDataConstructor;

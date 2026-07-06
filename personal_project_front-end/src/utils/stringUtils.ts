export const getPathResource = (
  originPath: string,
  nameFolder: string
): string => {
  return import.meta.env.VITE_API_PATH_RESOURCE + nameFolder + originPath;
};

export const isVietnamesePhoneNumber = (number: string) => {
  return /(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\b/.test(number);
};

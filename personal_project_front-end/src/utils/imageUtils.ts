export const validateImageFile = (file: File): boolean => {
  const acceptedFormats = [
    "image/jpeg",
    "image/png",
    "image/gif",
    "image/jpg",
    "image/bmp",
    "image/webp",
    "image/tiff",
  ];
  return acceptedFormats.includes(file.type);
};

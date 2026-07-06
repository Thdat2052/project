import { format, getMonth, getYear } from "date-fns";

export const formatDate = (date: Date): string => {
  return format(date, "dd/MM/yyyy HH:mm:ss");
};

export const months: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
export const FORMAT_DATE_DD_MM_YYY = "DD-MM-YYYY";
export const FORMAT_DATE_YYYY_MM_DD = "YYYY-MM-DD";
export const getCurrentMonth = (): number => getMonth(new Date()) + 1;

export const getCurrentYear = (): number => getYear(new Date());

export const generateYearOptions = (
  startYear: number,
  endYear: number
): { value: string; label: string }[] => {
  const years: { value: string; label: string }[] = [];

  for (let year = startYear; year <= endYear; year++) {
    years.push({ value: year.toString(), label: year.toString() });
  }

  return years;
};

export const getAllAliasMonthOfYear = (): string[] => {
  return [
    "Tháng 1",
    "Tháng 2",
    "Tháng 3",
    "Tháng 4",
    "Tháng 5",
    "Tháng 6",
    "Tháng 7",
    "Tháng 8",
    "Tháng 9",
    "Tháng 10",
    "Tháng 11",
    "Tháng 12",
  ];
};
export const formatDateToVietnamese = (dateString?: string | null) => {
  if (!dateString) return "N/A";
  const date = new Date(dateString);
  return isNaN(date.getTime()) ? "N/A" : date.toLocaleDateString("vi-VN");
};

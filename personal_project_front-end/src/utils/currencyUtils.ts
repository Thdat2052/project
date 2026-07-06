export const formatCurrency = (amount: number): string => {
  if (amount === null || isNaN(amount)) {
    return "0 ₫";
  }
  return amount.toLocaleString("vi-VN", {
    style: "currency",
    currency: "VND",
  });
};

export function vndToUsd(vndAmount: number, exchangeRate: number): number {
  if (vndAmount < 0 || exchangeRate < 0) {
    throw new Error("Số tiền VND và tỷ giá hối đoái phải lớn hơn 0");
  }

  const usdAmount = vndAmount * exchangeRate;

  return parseFloat(usdAmount.toFixed(2));
}
export const EXCHANGE_RATE = 0.000039;
export default 1;

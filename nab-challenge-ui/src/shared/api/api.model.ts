export interface ApiMultiRecordResponse<T> {
  recordCount: number;
  records: T[];
}

type PurchaseInfo = { priceFormatted: string; timeFormatted: string };
export interface BestProfitResponse {
  dateFormatted: string;
  buy: PurchaseInfo;
  sell: PurchaseInfo;
  profit: string;
}

export type Quote = {
  time: number;
  price: number;
};

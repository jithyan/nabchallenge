export interface ApiMultiRecordResponse<T> {
  recordCount: number;
  records: T[];
}

type PurchaseInfo = { price: string; time: string };
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

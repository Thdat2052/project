export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  page: number;
  limit: number;
}

export type PagingQueryConditionRequest = {
  pageSize: number;
  pageCurrent: number;
  sortBy?: string;
  sortType?: string;
  keyword?: string;
};

export const defaultPagingQueryConditionRequest: PagingQueryConditionRequest = {
  pageSize: 1000,
  pageCurrent: 0,
};

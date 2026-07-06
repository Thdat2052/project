import React, { useState, useEffect, useCallback, useMemo } from "react";
import { Select, Spin } from "antd";
import type { SelectProps } from "antd";
import debounce from "lodash/debounce";

export interface SelectLoadMoreParams {
  pageCurrent: number;
  pageSize: number;
  keyword: string;
}

export interface SelectLoadMoreResponse<T> {
  data: T[];
  total: number;
}

interface SelectLoadMoreProps<T extends Record<string, any>> {
  value?: string | string[];
  onChange?: (value: string | string[], option: any | any[]) => void;
  fetchOptions: (
    params: SelectLoadMoreParams
  ) => Promise<SelectLoadMoreResponse<T>>;
  renderOption: (option: T) => { label: React.ReactNode; value: string };
  placeholder?: string;
  mode?: "multiple" | "tags";
  initialData?: T[];
  selectProps?: Omit<
    SelectProps,
    | "options"
    | "value"
    | "onChange"
    | "onSearch"
    | "onPopupScroll"
    | "loading"
    | "filterOption"
    | "children"
    | "showSearch"
    | "placeholder"
    | "mode"
    | "notFoundContent"
  >;
  pageSize?: number;
  debounceTimeout?: number;
}

const DEFAULT_PAGE_SIZE = 50;
const DEFAULT_DEBOUNCE_TIMEOUT = 500;

function SelectLoadMore<T extends Record<string, any>>({
  value,
  onChange,
  fetchOptions,
  renderOption,
  placeholder,
  mode,
  initialData = [],
  selectProps = {},
  pageSize = DEFAULT_PAGE_SIZE,
  debounceTimeout = DEFAULT_DEBOUNCE_TIMEOUT,
}: SelectLoadMoreProps<T>) {
  const [options, setOptions] = useState<T[]>(initialData);
  const [loading, setLoading] = useState(false);
  const [searching, setSearching] = useState(false);
  const [pagination, setPagination] = useState({
    pageCurrent: 1,
    keyword: "",
  });
  const [hasMore, setHasMore] = useState(true);

  const loadOptionsInternal = useCallback(
    async (
      currentPage: number,
      currentKeyword: string,
      isNewSearch: boolean
    ) => {
      if (isNewSearch) setLoading(true);
      else if (hasMore) setSearching(true);

      try {
        const result = await fetchOptions({
          pageCurrent: currentPage - 1,
          pageSize: pageSize,
          keyword: currentKeyword,
        });

        const newItems = result.data;

        setOptions((prevOptions) => {
          let combinedOptions: T[];
          if (isNewSearch) {
            const initialDataValues = new Set(
              initialData.map((opt) => renderOption(opt).value)
            );
            const uniqueNewItems = newItems.filter(
              (item) => !initialDataValues.has(renderOption(item).value)
            );
            combinedOptions = [...initialData, ...uniqueNewItems];
          } else {
            const existingValues = new Set(
              prevOptions.map((opt) => renderOption(opt).value)
            );
            const uniqueNewItems = newItems.filter(
              (item) => !existingValues.has(renderOption(item).value)
            );
            combinedOptions = [...prevOptions, ...uniqueNewItems];
          }
          const uniqueMap = new Map<string, T>();
          combinedOptions.forEach((opt) => {
            const key = renderOption(opt).value;
            if (!uniqueMap.has(key)) uniqueMap.set(key, opt);
          });
          return Array.from(uniqueMap.values());
        });

        setHasMore(currentPage * pageSize < result.total);
      } finally {
        setLoading(false);
        setSearching(false);
      }
    },
    [fetchOptions, pageSize, initialData, renderOption, hasMore]
  );

  useEffect(() => {
    setOptions(initialData);
    setHasMore(true);
    setPagination((prev) => ({ ...prev, pageCurrent: 1 }));
    loadOptionsInternal(1, pagination.keyword, true);
  }, [pagination.keyword, initialData, loadOptionsInternal]);

  const debouncedSearch = useMemo(
    () =>
      debounce((searchKeyword: string) => {
        setPagination((prev) => ({
          ...prev,
          keyword: searchKeyword,
          pageCurrent: 1,
        }));
      }, debounceTimeout),
    [debounceTimeout]
  );

  const handlePopupScroll = (e: React.UIEvent<HTMLElement>) => {
    const { scrollTop, scrollHeight, clientHeight } = e.currentTarget;
    if (
      scrollHeight - scrollTop - clientHeight < 20 &&
      !searching &&
      hasMore &&
      !loading
    ) {
      const nextPage = pagination.pageCurrent + 1;
      setPagination((prev) => ({ ...prev, pageCurrent: nextPage }));
      loadOptionsInternal(nextPage, pagination.keyword, false);
    }
  };

  const mappedDisplayOptions = useMemo(
    () => options.map(renderOption),
    [options, renderOption]
  );

  return (
    <Select
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      mode={mode}
      options={mappedDisplayOptions}
      loading={loading}
      onSearch={debouncedSearch}
      onPopupScroll={handlePopupScroll}
      filterOption={false}
      showSearch
      notFoundContent={loading || searching ? <Spin size="small" /> : null}
      {...selectProps}
    />
  );
}

export default SelectLoadMore;

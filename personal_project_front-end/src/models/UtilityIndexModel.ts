export type UtilityIndexModel = {
  id?: number;
  roomId: number;
  electricityOldIndex: number;
  electricityNewIndex: number;
  waterOldIndex: number;
  waterNewIndex: number;
  waterUsage?: number;
  electricUsage?: number;
  monthMeasure?: number;
  yearMeasure?: number;
};

export const defaultUtilityIndexModel: UtilityIndexModel = {
  roomId: 0,
  electricityOldIndex: 0,
  electricityNewIndex: 0,
  waterOldIndex: 0,
  waterNewIndex: 0,
  waterUsage: 0,
  electricUsage: 0,
};

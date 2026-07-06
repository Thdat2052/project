
export type UtilityIndexResponse = {
    id?: number;
    roomId: number;
    electricityOldIndex: number;
    electricityNewIndex: number;
    waterOldIndex: number;
    waterNewIndex: number;
    waterUsage: number;
    electricUsage: number;
    monthMeasure: number;
    yearMeasure: number;
};
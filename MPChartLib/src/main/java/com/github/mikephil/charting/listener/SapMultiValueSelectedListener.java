package com.github.mikephil.charting.listener;

import com.github.mikephil.charting.data.SapSelectedDataSet;

public interface SapMultiValueSelectedListener {

    /**
     * Called when a value has been selected inside the chart.
     *
     * @param selectedData The selected Data sets highlighted
     */
    void onMultiValuesSelected(SapSelectedDataSet selectedData);

     /**
     * Called when nothing has been selected or an "un-select" has been made.
     */
     void onUnSelected();


}

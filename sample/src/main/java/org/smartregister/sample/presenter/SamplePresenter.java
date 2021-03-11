package org.smartregister.sample.presenter;

import org.smartregister.reporting.contract.ReportContract;
import org.smartregister.reporting.model.BaseReportIndicatorsModel;
import org.smartregister.reporting.domain.IndicatorQuery;
import org.smartregister.reporting.domain.IndicatorTally;
import org.smartregister.reporting.domain.ReportIndicator;
import org.smartregister.sample.interactor.SampleInteractor;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class SamplePresenter implements ReportContract.Presenter {

    private WeakReference<ReportContract.View> viewWeakReference;
    private ReportContract.Model model;
    private ReportContract.Interactor interactor;

    public SamplePresenter(ReportContract.View view) {
        this.viewWeakReference = new WeakReference<>(view);
        this.model = new BaseReportIndicatorsModel();
        this.interactor = new SampleInteractor();
    }

    @Override
    public void onResume() {
        getView().refreshUI();
    }

    @Override
    public List<Map<String, IndicatorTally>> fetchIndicatorsDailytallies() {
        return model.getIndicatorsDailyTallies();
    }

    @Override
    public void addIndicators(List<ReportIndicator> indicators) {
        for (ReportIndicator indicator : indicators) {
            model.addIndicator(indicator);
        }
    }

    @Override
    public void addIndicatorQueries(List<IndicatorQuery> indicatorQueries) {
        for (IndicatorQuery indicatorQuery : indicatorQueries) {
            model.addIndicatorQuery(indicatorQuery);
        }
    }

    @Override
    public void scheduleRecurringTallyJob() {
        interactor.scheduleDailyTallyJob();
    }


    public ReportContract.View getView() {
        if (viewWeakReference != null) {
            return viewWeakReference.get();
        }
        return null;
    }
}

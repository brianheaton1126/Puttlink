package com.golftronics.golfball.ble;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class PuttViewModel extends AndroidViewModel {

    private PuttRepository repository;
    private LiveData<List<PuttData>> allPutts;
    private LiveData<Integer> allLongPutts;
    public static int minDist;
    public static int maxDist;
    public long timeRange;

    //added 3-23: livedata for passing putt distance to fragment

    private MutableLiveData<Double> fragmentRollDistance = new MutableLiveData<>();
    public void setFragmentRollDistance(Double input) {
        fragmentRollDistance.setValue(input);
    }
    public LiveData<Double> getFragmentRollDistance() { return fragmentRollDistance; }


    private MutableLiveData<Double> fragmentMaxVelocity = new MutableLiveData<>();
    public void setFragmentMaxVelocity(Double input) {
        fragmentMaxVelocity.setValue(input);
    }
    public LiveData<Double> getFragmentMaxVelocity() { return fragmentMaxVelocity; }


    private MutableLiveData<Long> fragmentTimePuttTotal = new MutableLiveData<>();
    public void setFragmentTimePuttTotal(Long input) {
        fragmentTimePuttTotal.setValue(input);
    }
    public LiveData<Long> getFragmentTimePuttTotal() { return fragmentTimePuttTotal; }


    private MutableLiveData<Double> fragmentStimpUpslope = new MutableLiveData<>();
    public void setFragmentStimpUpslope(Double input) {
        fragmentStimpUpslope.setValue(input);
    }
    public LiveData<Double> getFragmentStimpUpslope() { return fragmentStimpUpslope; }


    private MutableLiveData<Double> fragmentStimpDownslope = new MutableLiveData<>();
    public void setFragmentStimpDownslope(Double input) {
        fragmentStimpDownslope.setValue(input);
    }
    public LiveData<Double> getFragmentStimpDownslope() { return fragmentStimpDownslope; }


    private MutableLiveData<Double> fragmentStimpSlopeCorrected = new MutableLiveData<>();
    public void setFragmentStimpSlopeCorrected(Double input) {fragmentStimpSlopeCorrected.setValue(input); }
    public LiveData<Double> getFragmentStimpSlopeCorrected() { return fragmentStimpSlopeCorrected; }


    private MutableLiveData<Double> fragmentPastHoleDist = new MutableLiveData<>();
    public void setFragmentPastHoleDist(Double input) {
        fragmentPastHoleDist.setValue(input);
    }
    public LiveData<Double> getFragmentPastHoleDist() { return fragmentPastHoleDist; }


    private MutableLiveData<Integer> fragmentGridPoint = new MutableLiveData<>();
    public void setFragmentGridPoint(Integer input) { fragmentGridPoint.setValue(input); }
    public LiveData<Integer> getFragmentGridPoint() { return fragmentGridPoint; }


    private MutableLiveData<Long> fragmentPuttPlotSession = new MutableLiveData<>();
    public void setFragmentPuttPlotSession(Long input) { fragmentPuttPlotSession.setValue(input); }
    public LiveData<Long> getFragmentPuttPlotSession() { return fragmentPuttPlotSession; }


    private MutableLiveData<Integer> fragmentPlotSessionVideoFlag = new MutableLiveData<>();
    public void setFragmentPlotSessionVideoFlag(Integer input) { fragmentPlotSessionVideoFlag.setValue(input); }
    public LiveData<Integer> getFragmentPlotSessionVideoFlag() { return fragmentPlotSessionVideoFlag; }


    private MutableLiveData<String> fragmentVideoDirectoryPath = new MutableLiveData<>();
    public void setFragmentVideoDirectoryPath(String input) { fragmentVideoDirectoryPath.setValue(input); }
    public LiveData<String> getFragmentVideoDirectoryPath() { return fragmentVideoDirectoryPath; }



    private MutableLiveData<Integer> fragmentStimpCardOpen = new MutableLiveData<>();
    public void setFragmentStimpCardOpen(Integer input) {
        fragmentStimpCardOpen.setValue(input);
    }
    public LiveData<Integer> getFragmentStimpCardOpen() { return fragmentStimpCardOpen; }

    private MutableLiveData<Integer> fragmentDrillsMenuOpen = new MutableLiveData<>();
    public void setFragmentDrillMenuOpen(Integer input) {
        fragmentDrillsMenuOpen.setValue(input);
    }
    public LiveData<Integer> getFragmentDrillsMenuOpen() { return fragmentDrillsMenuOpen; }


    private MutableLiveData<Integer> fragmentOverUnderCardOpen = new MutableLiveData<>();
    public void setFragmentOverUnderCardOpen(Integer input) {fragmentOverUnderCardOpen.setValue(input);
    }
    public LiveData<Integer> getFragmentOverUnderCardOpen() { return fragmentOverUnderCardOpen; }



    // livedata for passing target distance from practice fragement to control acitivity

    private MutableLiveData<Integer> fragmentTargetDistance = new MutableLiveData<>();
    public void setFragmentTargetDistance(Integer input) {
        fragmentTargetDistance.setValue(input);
    }
    public LiveData<Integer> getFragmentTargetDistance() { return fragmentTargetDistance; }


    //added 3-23:  livedata for passing putt stopped status to fragment

    private MutableLiveData<Integer> fragmentPuttStoppedFlag = new MutableLiveData<>();
    public void setFragmentPuttStoppedFlag(Integer input) {
        fragmentPuttStoppedFlag.setValue(input);
    }
    public LiveData<Integer> getFragmentPuttStoppedFlag() {
        return fragmentPuttStoppedFlag;
    }


    // livedata for passing Google signed in account name to fragments

    private MutableLiveData<String> fragmentSignedInAccount = new MutableLiveData<>();
    public void setFragmentSignedInAccount(String input) {
        fragmentSignedInAccount.setValue(input);
    }
    public LiveData<String> getFragmentSignedInAccount() {
        return fragmentSignedInAccount;
    }


    //live data for passing firebase UID to fragments
    private MutableLiveData<String> fragmentFirebaseUID = new MutableLiveData<>();
    public void setFragmentFirebaseUID(String input) {
        fragmentFirebaseUID.setValue(input);
    }
    public LiveData<String> getFragmentFirebaseUID() {
        return fragmentFirebaseUID;
    }




    private MutableLiveData<GoogleSignInAccount> fragmentGoogleSignedInAccount = new MutableLiveData<>();
    public void setFragmentGoogleSignedInAccount(GoogleSignInAccount input) {
        fragmentGoogleSignedInAccount.setValue(input);
    }
    public LiveData<GoogleSignInAccount> getFragmentGoogleSignedInAccount() {
        return fragmentGoogleSignedInAccount;
    }





    private MutableLiveData<Double> fragmentVelocity = new MutableLiveData<>();
    public void setFragmentVelocity(Double input) {
        fragmentVelocity.setValue(input);
    }
    public LiveData<Double> getFragmentVelocity() {
        return fragmentVelocity;
    }


    private MutableLiveData<Boolean> fragmentPuttMadeFlag = new MutableLiveData<>();
    public void setFragmentPuttMadeFlag(Boolean input) {fragmentPuttMadeFlag.setValue(input); }
    public LiveData<Boolean> getFragmentPuttMadeFlag() {
        return fragmentPuttMadeFlag;
    }


    private MutableLiveData<Integer> fragmentPuttAttempt = new MutableLiveData<>();
    public void setFragmentPuttAttempt(Integer input) {fragmentPuttAttempt.setValue(input); }
    public LiveData<Integer> getFragmentPuttAttempt() {
        return fragmentPuttAttempt;
    }



    private MutableLiveData<Double> fragmentVelocityEnd = new MutableLiveData<>();
    public void setFragmentVelocityEnd(Double input) {
        fragmentVelocityEnd.setValue(input);
    }
    public LiveData<Double> getFragmentVelocityEnd() {
        return fragmentVelocityEnd;
    }


    private MutableLiveData<Integer> fragmentMadeFlag = new MutableLiveData<>();
    public void setFragmentMadeFlag(Integer input) {
        fragmentMadeFlag.setValue(input);
    }
    public LiveData<Integer> getFragmentMadeFlag() {
        return fragmentMadeFlag;
    }


    private MutableLiveData<Integer> fragmentPuttMadeDistance = new MutableLiveData<>();
    public void setFragmentPuttMadeDistance(Integer input) { fragmentPuttMadeDistance.setValue(input); }
    public LiveData<Integer> getFragmentPuttMadeDistance() {
        return fragmentPuttMadeDistance;
    }


    private MutableLiveData<Integer> fragmentPuttEnd = new MutableLiveData<>();
    public void setFragmentPuttEnd(Integer input) { fragmentPuttEnd.setValue(input); }
    public LiveData<Integer> getFragmentPuttEnd() {
        return fragmentPuttEnd;
    }


    private MutableLiveData<Integer> fragmentPuttLaunchAngle = new MutableLiveData<>();
    public void setFragmentPuttLaunchAngle(Integer input) { fragmentPuttLaunchAngle.setValue(input); }
    public LiveData<Integer> getFragmentPuttLaunchAngle() {
        return fragmentPuttLaunchAngle;
    }


    private MutableLiveData<Double> fragmentAccelYAverage = new MutableLiveData<>();
    public void setFragmentAccelYAverage(Double input) { fragmentAccelYAverage.setValue(input); }
    public LiveData<Double> getFragmentAccelYAverage() {
        return fragmentAccelYAverage;
    }


    private MutableLiveData<CharSequence> fragmentPuttLaunchDirection = new MutableLiveData<>();
    public void setFragmentPuttLaunchDirection(CharSequence input) { fragmentPuttLaunchDirection.setValue(input); }
    public LiveData<CharSequence> getFragmentPuttLaunchDirection() { return fragmentPuttLaunchDirection; }


    private MutableLiveData<Long> fragmentPureRollPercentage = new MutableLiveData<>();
    public void setFragmentPureRollPercentage(Long input) { fragmentPureRollPercentage.setValue(input); }
    public LiveData<Long> getFragmentPureRollPercentage() { return fragmentPureRollPercentage; }


    private MutableLiveData<Long> fragmentTimeMaxtoStop = new MutableLiveData<>();
    public void setFragmentTimeMaxtoStop(Long input) { fragmentTimeMaxtoStop.setValue(input); }
    public LiveData<Long> getFragmentTimeMaxtoStop() { return fragmentTimeMaxtoStop; }


    private MutableLiveData<Double> fragmentSessionPuttMadeDistance = new MutableLiveData<>();
    public void setFragmentSessionPuttMadeDistance(Double input) { fragmentSessionPuttMadeDistance.setValue(input); }
    public LiveData<Double> getFragmentSessionPuttMadeDistance() { return fragmentSessionPuttMadeDistance; }


    private MutableLiveData<Integer> shotClockHighScore = new MutableLiveData<>();
    public void setShotClockHighScore(Integer input) {
        shotClockHighScore.setValue(input);
    }
    public LiveData<Integer> getShotClockHighScore() {
        return shotClockHighScore;
    }





    /*
    private MutableLiveData<Float> averageVelocityPuttsMade = new MutableLiveData<>();
    public void setAverageVelocityPuttsMade(Float input) {
        averageVelocityPuttsMade.setValue(input);
    }
    public LiveData<Float> getAverageVelocityPuttsMade() {
        return averageVelocityPuttsMade;
    }
    */










    public PuttViewModel(@NonNull Application application) {
        super(application);

        repository = new PuttRepository(application);
        //allPutts = repository.getAllPutts();
        //allLongPutts = repository.getAllLongPutts();
    }

    public void insert(PuttData puttsegment){

        repository.insert(puttsegment);

    }

    public void update(PuttData puttsegment){

        repository.update(puttsegment);

    }

    public void delete(PuttData puttsegment){

        repository.delete(puttsegment);

    }

    public void deleteAllPutts(){


    }

    public void updateLastPuttMade(){
        repository.updateLastPuttMade();
    }


    public MutableLiveData<Integer> allPuttsOverMinDist = new MutableLiveData<>();

    public void setAllPuttsOverMinDist(Integer input) {
        allPuttsOverMinDist.setValue(input);
        }

    public LiveData<Integer> getAllPuttsOverMinDist(int minDist, int maxDist){

        return repository.getAllPuttsOverMinDist(minDist, maxDist);
    }

    /*public LiveData<Integer> getAllPuttsOverMinDist(int minDist){
        return repository.getAllPuttsOverMinDist(minDist);


    }*/

    public MutableLiveData<Integer> madePutts = new MutableLiveData<>();

    public void setMadePutts(Integer input) {
        madePutts.setValue(input);
        }

        public LiveData<Integer> getMadePutts(long timeRange, int minDist, int maxDist){

        return repository.getMadePutts(timeRange, minDist, maxDist);
    }




    public MutableLiveData<Integer> allMadePutts = new MutableLiveData<>();

    public void setAllMadePutts(Integer input) {
        allMadePutts.setValue(input);
    }

    public LiveData<Integer> getAllMadePutts(int minDist, int maxDist){

        return repository.getAllMadePutts(minDist, maxDist);
    }




     /*   public LiveData<Integer> getMadePuttsToday(){

            return madePuttsToday;
        }*/



    public MutableLiveData<Integer> allPuttsToday = new MutableLiveData<>();

        public void setAllPuttsToday(Integer input) {
        allPuttsToday.setValue(input);

        }

        public LiveData<Integer> getAllPuttsToday(){

            return allPuttsToday;
        }


    public MutableLiveData<Integer> puttsOverMinDistByDate = new MutableLiveData<>();

        public void setPuttsOverMinDistByDate(Integer input){
            puttsOverMinDistByDate.setValue(input);
        }

        public LiveData<Integer> getPuttsOverMinDistByDate(long timeRange, int minDist, int maxDist){

            return repository.getPuttsOverMinDistByDate(timeRange, minDist, maxDist);
        }



     // read database for distance, time range, and slope

    public MutableLiveData<Integer> puttsOverMinDistByDateWithSlope = new MutableLiveData<>();

    public void setPuttsOverMinDistByDateWithSlope(Integer input){
        puttsOverMinDistByDateWithSlope.setValue(input);
    }

    public LiveData<Integer> getPuttsOverMinDistByDateWithSlope(long timeRange, int minDist, int maxDist, String slopeDirection){

        return repository.getPuttsOverMinDistByDateWithSlope(timeRange, minDist, maxDist, slopeDirection);
    }

    public MutableLiveData<Integer> madePuttsWithSlope = new MutableLiveData<>();

    public void setMadePuttsWithSlope(Integer input) {
        madePuttsWithSlope.setValue(input);
    }

    public LiveData<Integer> getMadePuttsWithSlope(long timeRange, int minDist, int maxDist, String slopeDirection){

        return repository.getMadePuttsWithSlope(timeRange, minDist, maxDist, slopeDirection);
    }



    // read database for distance, time range, and stimp

    public MutableLiveData<Integer> puttsOverMinDistByDateWithStimp = new MutableLiveData<>();

    public void setPuttsOverMinDistByDateWithStimp(Integer input){
        puttsOverMinDistByDateWithStimp.setValue(input);
    }

    public LiveData<Integer> getPuttsOverMinDistByDateWithStimp(long timeRange, int minDist, int maxDist, double stimpMin, double stimpMax){

        return repository.getPuttsOverMinDistByDateWithStimp(timeRange, minDist, maxDist, stimpMin, stimpMax);
    }

    public MutableLiveData<Integer> madePuttsWithStimp = new MutableLiveData<>();

    public void setMadePuttsWithStimp(Integer input) {
        madePuttsWithStimp.setValue(input);
    }

    public LiveData<Integer> getMadePuttsWithStimp(long timeRange, int minDist, int maxDist, double stimpMin, double stimpMax){

        return repository.getMadePuttsWithStimp(timeRange, minDist, maxDist, stimpMin, stimpMax);
    }











    // read database for distance, time range, slope, and stimp

    public MutableLiveData<Integer> puttsOverMinDistByDateWithSlopeWithStimp = new MutableLiveData<>();

    public void setPuttsOverMinDistByDateWithSlopeWithStimp(Integer input){
        puttsOverMinDistByDateWithSlope.setValue(input);
    }

    public LiveData<Integer> getPuttsOverMinDistByDateWithSlopeWithStimp(long timeRange, int minDist, int maxDist, String slopeDirection, double stimpMin, double stimpMax){

        return repository.getPuttsOverMinDistByDateWithSlopeWithStimp(timeRange, minDist, maxDist, slopeDirection, stimpMin, stimpMax);
    }

    public MutableLiveData<Integer> madePuttsWithSlopeWithStimp = new MutableLiveData<>();

    public void setMadePuttsWithSlopeWithStimp(Integer input) {
        madePuttsWithSlopeWithStimp.setValue(input);
    }

    public LiveData<Integer> getMadePuttsWithSlopeWithStimp(long timeRange, int minDist, int maxDist, String slopeDirection, double stimpMin, double stimpMax){

        return repository.getMadePuttsWithSlopeWithStimp(timeRange, minDist, maxDist, slopeDirection, stimpMin, stimpMax);
    }


    // read database for made status of last entry

    // read database for distance, time range, slope, and stimp

    public MutableLiveData<Integer> lastEntryMadeStatus = new MutableLiveData<>();

    public void setLastEntryMadeStatus(Integer input){
        lastEntryMadeStatus.setValue(input);
    }

    public LiveData<Integer> getLastEntryMadeStatus(){

        return repository.getLastEntryMadeStatus();
    }


    //read database for velocity of last made putt


    public MutableLiveData<Double> velocityLastMade = new MutableLiveData<>();

    public void setVelocityLastMade(Double input){ velocityLastMade.setValue(input);
    }

    public LiveData<Double> getVelocityLastMade(){

        return repository.getVelocityLastMade();
    }


    //read database for average velocity of made putts


    public MutableLiveData<Float> averageVelocityPuttsMade = new MutableLiveData<>();

    public void setAverageVelocityPuttsMade(Float input){averageVelocityPuttsMade.setValue(input);
    }

    public LiveData<Float> getAverageVelocityPuttsMade(){

        return repository.getAverageVelocityPuttsMade();
    }



    //read database for time until max velocity


    public MutableLiveData<Long> timeMaxVelocity = new MutableLiveData<>();

    public void setTimeMaxVelocity(Long input){timeMaxVelocity.setValue(input);
    }

    public LiveData<Long> getTimeMaxVelocity(long timePuttStart){

        return repository.getTimeMaxVelocity(timePuttStart);
    }



    //read database for putt start time


    public MutableLiveData<Long> timePuttStart = new MutableLiveData<>();

    public void setTimePuttStart(Long input){ timePuttStart.setValue(input);
    }

    public LiveData<Long> getTimePuttStart(){

        return repository.getTimePuttStart();
    }


    //read database for putt end time


    public MutableLiveData<Long> timePuttEnd = new MutableLiveData<>();

    public void setTimePuttEnd(Long input){ timePuttEnd.setValue(input);
    }

    public LiveData<Long> getTimePuttEnd(){

        return repository.getTimePuttEnd();
    }



    //read database for accely average


    public MutableLiveData<Float> accelYAverage = new MutableLiveData<>();

    public void setAccelYAverage(Float input){ accelYAverage.setValue(input);
    }

    public LiveData<Float> getAccelYAverage(long timePuttStart){

        return repository.getAccelYAverage(timePuttStart);
    }





    //read database for last session number


    public MutableLiveData<Integer> lastSessionNum = new MutableLiveData<>();

    public void setLastSessionNum(Integer input){ lastSessionNum.setValue(input);
    }

    public LiveData<Integer> getLastSessionNum(){

        return repository.getLastSessionNum();
    }







}

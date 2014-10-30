package com.kull.orm;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import com.kull.util.IModel;
import com.kull.util.Resultable;
import java.util.Set;
import com.kull.ObjectHelper;
import com.kull.orm.annotation.OrmTable;


 public class DefaultDBML {  


		


@OrmTable(name="car_valuation",pk="id" )
public  class car_valuation {

protected Integer Id; 
protected String Buying; 
protected String Maint; 
protected String Doors; 
protected String Persons; 
protected String LugBoot; 
protected String Safety; 
protected String Cls; 



public Integer getId(){ return this.Id; }
public void setId(Integer id){ this.Id=id; }

public String getBuying(){ return this.Buying; }
public void setBuying(String buying){ this.Buying=buying; }

public String getMaint(){ return this.Maint; }
public void setMaint(String maint){ this.Maint=maint; }

public String getDoors(){ return this.Doors; }
public void setDoors(String doors){ this.Doors=doors; }

public String getPersons(){ return this.Persons; }
public void setPersons(String persons){ this.Persons=persons; }

public String getLugBoot(){ return this.LugBoot; }
public void setLugBoot(String lugBoot){ this.LugBoot=lugBoot; }

public String getSafety(){ return this.Safety; }
public void setSafety(String safety){ this.Safety=safety; }

public String getCls(){ return this.Cls; }
public void setCls(String cls){ this.Cls=cls; }

} //end car_valuation

		


@OrmTable(name="airfoil_self_noise",pk="id" )
public  class airfoil_self_noise {

protected Integer Id; 
protected Integer FreqHertz; 
protected Double AngleAttackDegrees; 
protected Double ChrodLengthMeter; 
protected Double FreeStreamVelocityMeterPerSecond; 
protected Double SuctionSideDisplacementMeter; 
protected Double ScaledSoundPressureLevelDecibel; 



public Integer getId(){ return this.Id; }
public void setId(Integer id){ this.Id=id; }

public Integer getFreqHertz(){ return this.FreqHertz; }
public void setFreqHertz(Integer freqHertz){ this.FreqHertz=freqHertz; }

public Double getAngleAttackDegrees(){ return this.AngleAttackDegrees; }
public void setAngleAttackDegrees(Double angleAttackDegrees){ this.AngleAttackDegrees=angleAttackDegrees; }

public Double getChrodLengthMeter(){ return this.ChrodLengthMeter; }
public void setChrodLengthMeter(Double chrodLengthMeter){ this.ChrodLengthMeter=chrodLengthMeter; }

public Double getFreeStreamVelocityMeterPerSecond(){ return this.FreeStreamVelocityMeterPerSecond; }
public void setFreeStreamVelocityMeterPerSecond(Double freeStreamVelocityMeterPerSecond){ this.FreeStreamVelocityMeterPerSecond=freeStreamVelocityMeterPerSecond; }

public Double getSuctionSideDisplacementMeter(){ return this.SuctionSideDisplacementMeter; }
public void setSuctionSideDisplacementMeter(Double suctionSideDisplacementMeter){ this.SuctionSideDisplacementMeter=suctionSideDisplacementMeter; }

public Double getScaledSoundPressureLevelDecibel(){ return this.ScaledSoundPressureLevelDecibel; }
public void setScaledSoundPressureLevelDecibel(Double scaledSoundPressureLevelDecibel){ this.ScaledSoundPressureLevelDecibel=scaledSoundPressureLevelDecibel; }

} //end airfoil_self_noise

		


@OrmTable(name="iris",pk="id" )
public  class iris {

protected Integer Id; 
protected Double SepalLength; 
protected Double SepalWidth; 
protected Double PetalLength; 
protected Double PetalWidth; 
protected String Cls; 



public Integer getId(){ return this.Id; }
public void setId(Integer id){ this.Id=id; }

public Double getSepalLength(){ return this.SepalLength; }
public void setSepalLength(Double sepalLength){ this.SepalLength=sepalLength; }

public Double getSepalWidth(){ return this.SepalWidth; }
public void setSepalWidth(Double sepalWidth){ this.SepalWidth=sepalWidth; }

public Double getPetalLength(){ return this.PetalLength; }
public void setPetalLength(Double petalLength){ this.PetalLength=petalLength; }

public Double getPetalWidth(){ return this.PetalWidth; }
public void setPetalWidth(Double petalWidth){ this.PetalWidth=petalWidth; }

public String getCls(){ return this.Cls; }
public void setCls(String cls){ this.Cls=cls; }

} //end iris

		


@OrmTable(name="wilt_training",pk="id" )
public  class wilt_training {

protected Integer Id; 
protected String Class; 
protected Double GlcmPan; 
protected Double MeanGreen; 
protected Double MeanRed; 
protected Double MeanNir; 
protected Double SdPan; 



public Integer getId(){ return this.Id; }
public void setId(Integer id){ this.Id=id; }



public Double getGlcmPan(){ return this.GlcmPan; }
public void setGlcmPan(Double glcmPan){ this.GlcmPan=glcmPan; }

public Double getMeanGreen(){ return this.MeanGreen; }
public void setMeanGreen(Double meanGreen){ this.MeanGreen=meanGreen; }

public Double getMeanRed(){ return this.MeanRed; }
public void setMeanRed(Double meanRed){ this.MeanRed=meanRed; }

public Double getMeanNir(){ return this.MeanNir; }
public void setMeanNir(Double meanNir){ this.MeanNir=meanNir; }

public Double getSdPan(){ return this.SdPan; }
public void setSdPan(Double sdPan){ this.SdPan=sdPan; }

} //end wilt_training

		


@OrmTable(name="poker_hand_training_true",pk="id" )
public  class poker_hand_training_true {

protected String RowNames; 
protected Integer Suit1; 
protected Integer Rank1; 
protected Integer Suit2; 
protected Integer Rank2; 
protected Integer Suit3; 
protected Integer Rank3; 
protected Integer Suit4; 
protected Integer Rank4; 
protected Integer Suit5; 
protected Integer Rank5; 
protected Integer Cls; 



public String getRowNames(){ return this.RowNames; }
public void setRowNames(String rowNames){ this.RowNames=rowNames; }

public Integer getSuit1(){ return this.Suit1; }
public void setSuit1(Integer suit1){ this.Suit1=suit1; }

public Integer getRank1(){ return this.Rank1; }
public void setRank1(Integer rank1){ this.Rank1=rank1; }

public Integer getSuit2(){ return this.Suit2; }
public void setSuit2(Integer suit2){ this.Suit2=suit2; }

public Integer getRank2(){ return this.Rank2; }
public void setRank2(Integer rank2){ this.Rank2=rank2; }

public Integer getSuit3(){ return this.Suit3; }
public void setSuit3(Integer suit3){ this.Suit3=suit3; }

public Integer getRank3(){ return this.Rank3; }
public void setRank3(Integer rank3){ this.Rank3=rank3; }

public Integer getSuit4(){ return this.Suit4; }
public void setSuit4(Integer suit4){ this.Suit4=suit4; }

public Integer getRank4(){ return this.Rank4; }
public void setRank4(Integer rank4){ this.Rank4=rank4; }

public Integer getSuit5(){ return this.Suit5; }
public void setSuit5(Integer suit5){ this.Suit5=suit5; }

public Integer getRank5(){ return this.Rank5; }
public void setRank5(Integer rank5){ this.Rank5=rank5; }

public Integer getCls(){ return this.Cls; }
public void setCls(Integer cls){ this.Cls=cls; }

} //end poker_hand_training_true

		


@OrmTable(name="adult",pk="id" )
public  class adult {

protected Integer Id; 
protected Integer Age; 
protected String Workclass; 
protected Integer Fnlwgt; 
protected String Education; 
protected Integer EducationNum; 
protected String MaritalStatus; 
protected String Occupation; 
protected String Relationship; 
protected String Race; 
protected String Sex; 
protected Integer CapitalGain; 
protected Integer CapitalLoss; 
protected Integer HoursPerWeeek; 
protected String NativeCountry; 
protected String Income; 



public Integer getId(){ return this.Id; }
public void setId(Integer id){ this.Id=id; }

public Integer getAge(){ return this.Age; }
public void setAge(Integer age){ this.Age=age; }

public String getWorkclass(){ return this.Workclass; }
public void setWorkclass(String workclass){ this.Workclass=workclass; }

public Integer getFnlwgt(){ return this.Fnlwgt; }
public void setFnlwgt(Integer fnlwgt){ this.Fnlwgt=fnlwgt; }

public String getEducation(){ return this.Education; }
public void setEducation(String education){ this.Education=education; }

public Integer getEducationNum(){ return this.EducationNum; }
public void setEducationNum(Integer educationNum){ this.EducationNum=educationNum; }

public String getMaritalStatus(){ return this.MaritalStatus; }
public void setMaritalStatus(String maritalStatus){ this.MaritalStatus=maritalStatus; }

public String getOccupation(){ return this.Occupation; }
public void setOccupation(String occupation){ this.Occupation=occupation; }

public String getRelationship(){ return this.Relationship; }
public void setRelationship(String relationship){ this.Relationship=relationship; }

public String getRace(){ return this.Race; }
public void setRace(String race){ this.Race=race; }

public String getSex(){ return this.Sex; }
public void setSex(String sex){ this.Sex=sex; }

public Integer getCapitalGain(){ return this.CapitalGain; }
public void setCapitalGain(Integer capitalGain){ this.CapitalGain=capitalGain; }

public Integer getCapitalLoss(){ return this.CapitalLoss; }
public void setCapitalLoss(Integer capitalLoss){ this.CapitalLoss=capitalLoss; }

public Integer getHoursPerWeeek(){ return this.HoursPerWeeek; }
public void setHoursPerWeeek(Integer hoursPerWeeek){ this.HoursPerWeeek=hoursPerWeeek; }

public String getNativeCountry(){ return this.NativeCountry; }
public void setNativeCountry(String nativeCountry){ this.NativeCountry=nativeCountry; }

public String getIncome(){ return this.Income; }
public void setIncome(String income){ this.Income=income; }

} //end adult

		


@OrmTable(name="wilt_testing",pk="id" )
public  class wilt_testing {

protected Integer Id; 
protected String Class; 
protected Double GlcmPan; 
protected Double MeanGreen; 
protected Double MeanRed; 
protected Double MeanNir; 
protected Double SdPan; 



public Integer getId(){ return this.Id; }
public void setId(Integer id){ this.Id=id; }



public Double getGlcmPan(){ return this.GlcmPan; }
public void setGlcmPan(Double glcmPan){ this.GlcmPan=glcmPan; }

public Double getMeanGreen(){ return this.MeanGreen; }
public void setMeanGreen(Double meanGreen){ this.MeanGreen=meanGreen; }

public Double getMeanRed(){ return this.MeanRed; }
public void setMeanRed(Double meanRed){ this.MeanRed=meanRed; }

public Double getMeanNir(){ return this.MeanNir; }
public void setMeanNir(Double meanNir){ this.MeanNir=meanNir; }

public Double getSdPan(){ return this.SdPan; }
public void setSdPan(Double sdPan){ this.SdPan=sdPan; }

} //end wilt_testing

		


@OrmTable(name="wine",pk="id" )
public  class wine {

protected Integer Id; 
protected Integer Cultivar; 
protected Double Alcohol; 
protected Double AmlicAcid; 
protected Double Ash; 
protected Double AlcalintityAsh; 
protected Integer Magnesium; 
protected Double TotalPhenols; 
protected Double Flavanoids; 
protected Double NonflavanoidPhenols; 
protected Double Proanthocyanins; 
protected Double ColorIntensity; 
protected Double Hue; 
protected Double Diuted; 
protected Integer Proline; 



public Integer getId(){ return this.Id; }
public void setId(Integer id){ this.Id=id; }

public Integer getCultivar(){ return this.Cultivar; }
public void setCultivar(Integer cultivar){ this.Cultivar=cultivar; }

public Double getAlcohol(){ return this.Alcohol; }
public void setAlcohol(Double alcohol){ this.Alcohol=alcohol; }

public Double getAmlicAcid(){ return this.AmlicAcid; }
public void setAmlicAcid(Double amlicAcid){ this.AmlicAcid=amlicAcid; }

public Double getAsh(){ return this.Ash; }
public void setAsh(Double ash){ this.Ash=ash; }

public Double getAlcalintityAsh(){ return this.AlcalintityAsh; }
public void setAlcalintityAsh(Double alcalintityAsh){ this.AlcalintityAsh=alcalintityAsh; }

public Integer getMagnesium(){ return this.Magnesium; }
public void setMagnesium(Integer magnesium){ this.Magnesium=magnesium; }

public Double getTotalPhenols(){ return this.TotalPhenols; }
public void setTotalPhenols(Double totalPhenols){ this.TotalPhenols=totalPhenols; }

public Double getFlavanoids(){ return this.Flavanoids; }
public void setFlavanoids(Double flavanoids){ this.Flavanoids=flavanoids; }

public Double getNonflavanoidPhenols(){ return this.NonflavanoidPhenols; }
public void setNonflavanoidPhenols(Double nonflavanoidPhenols){ this.NonflavanoidPhenols=nonflavanoidPhenols; }

public Double getProanthocyanins(){ return this.Proanthocyanins; }
public void setProanthocyanins(Double proanthocyanins){ this.Proanthocyanins=proanthocyanins; }

public Double getColorIntensity(){ return this.ColorIntensity; }
public void setColorIntensity(Double colorIntensity){ this.ColorIntensity=colorIntensity; }

public Double getHue(){ return this.Hue; }
public void setHue(Double hue){ this.Hue=hue; }

public Double getDiuted(){ return this.Diuted; }
public void setDiuted(Double diuted){ this.Diuted=diuted; }

public Integer getProline(){ return this.Proline; }
public void setProline(Integer proline){ this.Proline=proline; }

} //end wine

		


@OrmTable(name="poker_hand_testing",pk="id" )
public  class poker_hand_testing {

protected String RowNames; 
protected Integer Suit1; 
protected Integer Rank1; 
protected Integer Suit2; 
protected Integer Rank2; 
protected Integer Suit3; 
protected Integer Rank3; 
protected Integer Suit4; 
protected Integer Rank4; 
protected Integer Suit5; 
protected Integer Rank5; 
protected Integer Cls; 



public String getRowNames(){ return this.RowNames; }
public void setRowNames(String rowNames){ this.RowNames=rowNames; }

public Integer getSuit1(){ return this.Suit1; }
public void setSuit1(Integer suit1){ this.Suit1=suit1; }

public Integer getRank1(){ return this.Rank1; }
public void setRank1(Integer rank1){ this.Rank1=rank1; }

public Integer getSuit2(){ return this.Suit2; }
public void setSuit2(Integer suit2){ this.Suit2=suit2; }

public Integer getRank2(){ return this.Rank2; }
public void setRank2(Integer rank2){ this.Rank2=rank2; }

public Integer getSuit3(){ return this.Suit3; }
public void setSuit3(Integer suit3){ this.Suit3=suit3; }

public Integer getRank3(){ return this.Rank3; }
public void setRank3(Integer rank3){ this.Rank3=rank3; }

public Integer getSuit4(){ return this.Suit4; }
public void setSuit4(Integer suit4){ this.Suit4=suit4; }

public Integer getRank4(){ return this.Rank4; }
public void setRank4(Integer rank4){ this.Rank4=rank4; }

public Integer getSuit5(){ return this.Suit5; }
public void setSuit5(Integer suit5){ this.Suit5=suit5; }

public Integer getRank5(){ return this.Rank5; }
public void setRank5(Integer rank5){ this.Rank5=rank5; }

public Integer getCls(){ return this.Cls; }
public void setCls(Integer cls){ this.Cls=cls; }

} //end poker_hand_testing

		


@OrmTable(name="wholesale_customers",pk="id" )
public  class wholesale_customers {

protected Integer Id; 
protected Integer Channel; 
protected Integer Region; 
protected Integer Fresh; 
protected Integer Milk; 
protected Integer Grocery; 
protected Integer Frozen; 
protected Integer DetergentsPaper; 
protected Integer Delicassen; 



public Integer getId(){ return this.Id; }
public void setId(Integer id){ this.Id=id; }

public Integer getChannel(){ return this.Channel; }
public void setChannel(Integer channel){ this.Channel=channel; }

public Integer getRegion(){ return this.Region; }
public void setRegion(Integer region){ this.Region=region; }

public Integer getFresh(){ return this.Fresh; }
public void setFresh(Integer fresh){ this.Fresh=fresh; }

public Integer getMilk(){ return this.Milk; }
public void setMilk(Integer milk){ this.Milk=milk; }

public Integer getGrocery(){ return this.Grocery; }
public void setGrocery(Integer grocery){ this.Grocery=grocery; }

public Integer getFrozen(){ return this.Frozen; }
public void setFrozen(Integer frozen){ this.Frozen=frozen; }

public Integer getDetergentsPaper(){ return this.DetergentsPaper; }
public void setDetergentsPaper(Integer detergentsPaper){ this.DetergentsPaper=detergentsPaper; }

public Integer getDelicassen(){ return this.Delicassen; }
public void setDelicassen(Integer delicassen){ this.Delicassen=delicassen; }

} 
 
 }//end wholesale_customers
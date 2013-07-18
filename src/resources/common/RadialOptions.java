/*******************************************************************************
 * Copyright (c) 2013 <Project SWG>
 * 
 * This File is part of NGECore2.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
 * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
 ******************************************************************************/
package resources.common;

public class RadialOptions {
	
    public static int unknown	= 0;
    public static int combatTarget	= 1;
    public static int combatUntarget	= 2;
    public static int combatAttack	= 3;
    public static int combatPeace	= 4;
    public static int combatDuel	= 5;
    public static int combatDeathBlow	= 6;
    public static int examine	= 7;     
    public static int examineCharacterSheet	= 8;
    public static int tradeStart	= 9;
    public static int tradeAccept	= 10;
    public static int itemPickup	= 11;
    public static int itemEquip	= 12;
    public static int itemUnequip	= 13;
    public static int itemDrop	= 14;
    public static int itemDestroy = 15;
    public static int itemToken	= 16;
    public static int itemOpen	= 17;
    public static int itemOpenNewWindow	= 18;
    public static int itemActivate	= 19;
    public static int itemDeactivate = 20;
    public static int itemUse	= 21;
    public static int itemUseSelf	= 22;
    public static int itemUseOther	= 23;
    public static int itemSit	= 24;
    public static int itemMail = 25;
    public static int converseStart	= 26;
    public static int converseRespond	= 27;
    public static int converseResponse	= 28; // err
    public static int converseStop	= 29;
    public static int craftOptions	= 30;
    public static int craftStart	= 31;
    public static int craftHopperInput	= 32;
    public static int craftHopperOutput = 33;
    public static int missionTerminalList	= 34;
    public static int missionDetails	= 35; 
    public static int loot	= 36;
    public static int lootAll	= 37;
    public static int groupInvite	= 38;
    public static int groupJoin	= 39;
    public static int groupLeave	= 40;
    public static int groupKick	= 41;
    public static int groupDisband	= 42;
    public static int groupDecline	= 43;
    public static int itemRemoveFromCrate = 44;
    public static int petCall = 45;
    public static int terminalAuctionUse = 46;
    public static int creatureFollow	= 47;
    public static int creatureStopFollow	= 48;
    public static int split	= 49; // err
    public static int imageDesign	= 50;
    public static int setName	= 51;
    public static int itemRotate	= 52;
    public static int itemRotateRight	= 53;
    public static int itemRotateLeft	= 54;
    public static int itemMove	= 55;
    public static int itemMoveForward	= 56;
    public static int itemMoveBack	= 57;
    public static int itemMoveUp	= 58;
    public static int itemMoveDown	= 59;
    public static int petStore	= 60;
    public static int vehicleGenerate = 61;
    public static int vehicleStore = 62;
    public static int missionAbort = 63;
    public static int missionEndDuty	= 64;
    public static int shipManageComponents	= 65;
    public static int waypointAutopilot	= 66;
    public static int programDroid	= 67;   
    public static int vehicleOfferRide	= 68; // Unused
    public static int itemPublicContainerUse	= 69; // Use
    public static int creatureShowCollections	= 70;
    public static int groupMakeMasterLooter	= 71;
    public static int groupMakeLeader	= 72;
    public static int groupLootOptions	= 73;
    public static int itemRotateForward	= 74;
    public static int itemRotateBackward	= 75;
    public static int itemRotateClockwise	= 76;
    public static int itemRotateCounterclockwise	= 77;
    public static int itemRotateRandom = 78;
    public static int itemRotateRandomYaw = 79;
    public static int itemRotateRandomPitch = 80;
    public static int itemRotateRandomRoll = 81;
    public static int itemRotateReset = 82;
    public static int itemRotateCopy = 83;
    public static int itemCopyLocation = 84;
    public static int itemCopyHeight = 85;
    public static int groupTell = 86;
    public static int itemWpSetColor = 87;
    public static int itemWpColorBlue = 88;
    public static int itemWpColorGreen = 89;
    public static int itemWpColorOrange = 90;
    public static int itemWpColorYellow = 91;
    public static int itemWpColorPurple = 92;
    public static int itemWpColorWhite = 93;
    public static int itemMoveLeft = 94;
    public static int itemMoveRight = 95;
    public static int itemRotationApply = 96;
    public static int itemRotationReset = 97;
    public static int windowLock = 98;
    public static int windowUnlock	= 99;
    public static int groupCreatePickupPoint	= 100;
    public static int groupUsePickupPoint	= 101;
    public static int groupUsePickupPointNoCamp	= 102;
    public static int voiceShortlistRemove = 103;
    public static int voiceInvite	= 104;
    public static int voiceKick = 105;
    public static int itemEquipAppearance	= 106;
    public static int itemUnequipAppearance	= 107;
    public static int openStorytellerRecipe	= 108;
    public static int itemWpMakeCity	= 109;
    public static int itemWpMakeGuild	= 110;
    public static int atmosphericFly	= 111;
    public static int atmosphericLand	= 112;
    public static int bankTransfer	= 113; //serverDivider
    public static int bankitems	= 114; //serverMenu1
    public static int bankWithdrawAll	= 115; //serverMenu2
    public static int bankDepositAll	= 116; //serverMenu3
    public static int serverMenu4	= 117;
    public static int serverMenu5	= 118;
    public static int serverMenu6	= 119;
    public static int serverMenu7	= 120;
    public static int serverMenu8	= 121;
    public static int serverMenu9	= 122;
    public static int serverMenu10	= 123;
    public static int serverMenu11	= 124;
    public static int serverMenu12	= 125;
    public static int serverMenu13	= 126;
    public static int serverMenu14	= 127;
    public static int serverMenu15	= 128;
    public static int serverMenu16	= 129;
    public static int serverMenu17	= 130;
    public static int serverMenu18	= 131;
    public static int serverMenu19	= 132;
    public static int serverMenu20	= 133;
    public static int serverMenu21	= 134;
    public static int serverMenu22	= 135;
    public static int serverMenu23  = 136;
    public static int serverMenu24	= 137;
    public static int serverMenu25	= 138;
    public static int serverMenu26	= 139;
    public static int serverMenu27	= 140;
    public static int serverMenu28	= 141;
    public static int serverMenu29	= 142;
    public static int serverMenu30	= 143;
    public static int serverMenu31	= 144;
    public static int serverMenu32	= 145;
    public static int serverMenu33	= 146;
    public static int serverMenu34	= 147;
    public static int serverMenu35	= 148;
    public static int serverMenu36	= 149;
    public static int serverMenu37	= 150;
    public static int serverMenu38	= 151;
    public static int serverMenu39	= 152;
    public static int serverMenu40	= 153;
    public static int serverMenu41	= 154;
    public static int serverMenu42	= 155;
    public static int serverMenu43	= 156;
    public static int serverMenu44	= 157;
    public static int serverMenu45	= 158;
    public static int serverMenu46	= 159;
    public static int serverMenu47	= 160;
    public static int serverMenu48	= 161;
    public static int serverMenu49	= 162;
    public static int serverMenu50	= 163;
    public static int serverHarvesterManage	= 164; //Reused for HarvestMeat
    public static int serverHouseManage	= 165; //Reused for HarvestHide
    public static int serverFactionHallManage	= 166; //Reused for HarvestBone
    public static int serverHue	= 167; //Reused for Milk Me
    public static int serverObserve	= 168;
    public static int serverStopObserving	= 169;
    public static int serverTravelOptions	= 170;
    public static int serverBazaarOptions	= 171;
    public static int serverShippingOptions	= 172;
    public static int serverHealWound	= 173;
    public static int serverHealWoundHealth	= 174;
    public static int serverHealWoundAction	= 175;
    public static int serverHealWoundStrength	= 176;
    public static int serverHealWoundConstitution	= 177;
    public static int serverHealWoundQuickness	= 178;
    public static int serverHealWoundStamina	= 179;
    public static int serverHealDamage	= 180;
    public static int serverHealState	= 181;
    public static int serverHealStateStunned	= 182;
    public static int serverHealStateBlinded	= 183;
    public static int serverHealStateDizzy	= 184;
    public static int serverHealStateIntimidated	= 185;
    public static int serverHealEnhance	= 186;
    public static int serverHealEnhanceHealth	= 187;
    public static int serverHealEnhanceAction	= 188;
    public static int serverHealEnhanceStrength	= 189;
    public static int serverHealEnhanceConstitution	= 190;
    public static int serverHealEnhanceQuickness	= 191;
    public static int serverHealEnhanceStamina	= 192;
    public static int serverHealFirstaid	= 193;
    public static int serverHealCurePoison	= 194;
    public static int serverHealCureDisease	= 195;
    public static int serverHealApplyPoison	= 196;
    public static int serverHealApplyDisease	= 197;
    public static int serverHarvest	= 198;
    public static int serverHarvestCorpse	= 199;
    public static int serverPerformanceListen	= 200;
    public static int serverPerformanceWatch	= 201;
    public static int serverPerformanceListenStop	= 202;
    public static int serverPerformanceWatchStop	= 203;
    public static int serverTerminalPermissions	= 204;
    public static int serverTerminalManagement	= 205;
    public static int serverTerminalPermissionsEnter	= 206;
    public static int serverTerminalPermissionsBanned	= 207;
    public static int serverTerminalPermissionsAdmin	= 208;
    public static int serverTerminalPermissionsVendor	= 209;
    public static int serverTerminalPermissionsHopper	= 210;
    public static int serverTerminalManagementStatus	= 211;
    public static int serverTerminalManagementPrivacy	= 212;
    public static int serverTerminalManagementTransfer	= 213;
    public static int serverTerminalManagementResidence	= 214;
    public static int serverTerminalManagementDestroy	= 215;
    public static int serverTerminalManagementPay	= 216;
    public static int serverTerminalCreateVendor	= 217;
    public static int serverGiveVendorMaintenance	= 218;
    public static int serverItemOptions	= 219;
    public static int serverSurveyToolRange	= 220;
    public static int serverSurveyToolResolution	= 221;
    public static int serverSurveyToolClass	= 222;
    public static int serverProbeDroidTrackTarget	= 223;
    public static int serverProbeDroidFindTarget	= 224;
    public static int serverProbeDroidActivate	= 225;
    public static int serverProbeDroidBuy	= 226;
    public static int serverTeach	= 227;
    public static int petCommand	= 228;
    public static int petFollow	= 229;
    public static int petStay	= 230;
    public static int petGuard	= 231;
    public static int petFriend	= 232;
    public static int petAttack	= 233;
    public static int petPatrol	= 234;
    public static int petGetPatrolPoint	= 235;
    public static int petClearPatrolPoints	= 236;
    public static int petAssumeFormation1	= 237;
    public static int petAssumeFormation2	= 238;
    public static int petTransfer	= 239;
    public static int petRelease	= 240;
    public static int petTrick1	= 241;
    public static int petTrick2	= 242;
    public static int petTrick3	= 243;
    public static int petTrick4	= 244;
    public static int petGroup	= 245;
    public static int petTame	= 246;
    public static int petFeed	= 247;
    public static int petSpecialAttack1	= 248;
    public static int petSpecialAttack2	= 249;
    public static int petRangedAttack	= 250;
    public static int diceRoll	= 251;
    public static int diceTwoFace	= 252;
    public static int diceThreeFace = 253;
    public static int diceFourFace = 254;
    public static int diceFiveFace = 255;
    public static int diceSixFace = 256;
    public static int diceSevenFace = 257;
    public static int diceEightFace = 258;
    public static int diceCountOne = 259;
    public static int diceCountTwo = 260;
    public static int diceCountThree = 261;
    public static int diceCountFour = 262;
    public static int createBallot = 263;
    public static int vote = 264;
    public static int bombingRun = 265;
    public static int selfDestruct = 266;
    public static int thirtySec = 267;
    public static int fifteenSec = 268;
    public static int serverCampDisband = 269;
    public static int serverCampAssumeOwnership = 270;
    public static int serverProbeDroidProgram = 271;
    public static int serverGuildCreate = 272;
    public static int serverGuildInfo = 273;
    public static int serverGuildMembers = 274;
    public static int serverGuildSponsored = 275;
    public static int serverGuildEnemies = 276;
    public static int serverGuildSponsor = 277;
    public static int serverGuildDisband = 278;
    public static int serverGuildNamechance = 279;
    public static int serverGuildGuildManagement = 280;
    public static int serverGuildMemberManagement = 281;
    public static int serverManfHopperInput = 282;
    public static int serverManfHopperOutput = 283;
    public static int elevatorUp = 284;
    public static int elevatorDown = 285;
    public static int serverPetOpen = 286;
    public static int serverPetDpad = 287;
    public static int serverMedToolDiagnose = 288;
    public static int serverMedToolTendwound = 289;
    public static int serverMedToolTenddamage = 290;
    public static int serverPetMount = 291;
    public static int serverPetDismount = 292;
    public static int serverPetTrainMount = 293;
    public static int serverVehicleEnter = 294;
    public static int serverVehicleExit = 295;
    public static int serverVehicleEnterExit = 296;
    public static int openNavicompDpad = 297;
    public static int initNavicompDpad = 298;
    public static int cityStatus = 299;
    public static int cityCitizens = 300;
    public static int cityStructures = 301;
    public static int cityTreasury = 302;
    public static int cityManagement = 303;
    public static int cityName = 304;
    public static int cityMilitia = 305;
    public static int cityTaxes = 306;
    public static int cityTreasuryDeposit = 307;
    public static int cityTreasuryWithdraw = 308;
    public static int cityRegister = 309;
    public static int cityRank = 310;
    public static int cityAdmin1 = 311;
    public static int cityAdmin2 = 312;
    public static int cityAdmin3 = 313;
    public static int cityAdmin4 = 314;
    public static int cityAdmin5 = 315;
    public static int cityAdmin6 = 316;
    public static int memoryChipProgram = 317;
    public static int memoryChipTransfer = 318;
    public static int memoryChipAnalyze = 319;
    public static int equipDroidOnShip = 320;
    public static int bioLink = 321;
    public static int landmineDisarm = 322;
    public static int landmineReverseTrigger = 323;
    public static int rewardTradeIn = 324;
    public static int removeNoTrade = 325;
    public static int cityFurniturePlace = 326;
    public static int cityFurnitureRemove = 327;
    public static int cityFurnitureAlign = 328;
    public static int cityFurnitureAlignNorth = 329;
    public static int cityFurnitureAlignSouth = 330;
    public static int cityFurnitureAlignEast = 331;
    public static int cityFurnitureAlignWest = 332;
    public static int fishingRetrieveFish = 333;
    
	private byte parentId;
	private byte optionId;
	private byte optionType;
	private String description;
    
    public RadialOptions(byte parentId, byte optionId, byte optionType, String description) {
    	this.setParentId(parentId);
    	this.setOptionId(optionId);
    	this.setOptionType(optionType);
    	this.setDescription(description);
    }

	public RadialOptions() {
		// TODO Auto-generated constructor stub
	}

	public byte getParentId() {
		return parentId;
	}

	public void setParentId(byte parentId) {
		this.parentId = parentId;
	}

	public byte getOptionId() {
		return optionId;
	}

	public void setOptionId(byte optionId) {
		this.optionId = optionId;
	}

	public byte getOptionType() {
		return optionType;
	}

	public void setOptionType(byte optionType) {
		this.optionType = optionType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

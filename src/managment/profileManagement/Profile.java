package managment.profileManagement;

import managment.playerManagement.Player;
import security.loadSuppliers.bonusSupplier.BonusData;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public final class Profile {
    private String name;

    public int getRank() {
        return rank;
    }

    private int rank;
    private Integer MMR;
    private Integer win;
    private Integer lose;
    private Integer winForDevourer;
    private Integer winForLV;
    private Integer winForOrcBash;

    private BonusData bonusData;

    public Profile(String name, int rank, Integer MMR, Integer win, Integer lose, Integer winForDevourer,
                   Integer winForLV, Integer winForOrcBash, BonusData bonusData) {
        this.name = name;
        this.rank = rank;
        this.MMR = MMR;
        this.win = win;
        this.lose = lose;
        this.winForDevourer = winForDevourer;
        this.winForLV = winForLV;
        this.winForOrcBash = winForOrcBash;
        this.bonusData = bonusData;
    }

    public Profile(String name, int rank, Integer MMR, Integer win, Integer lose, Integer winForDevourer,
                   Integer winForLV, Integer winForOrcBash) {
        this.name = name;
        this.rank = rank;
        this.MMR = MMR;
        this.win = win;
        this.lose = lose;
        this.winForDevourer = winForDevourer;
        this.winForLV = winForLV;
        this.winForOrcBash = winForOrcBash;
    }

    //Изменяем ранг профию:
    public void correctStatistics(Player player, Profile opponentProfile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src\\Profiles\\" + this.getName() + ".hoa")));
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("src\\Profiles\\" + this.getName() + ".hoa")));

        if (player.isWinner()) {
            for (int i = 0; i < 8; i++) {
                switch (i) {
                    case 2: //Коррекция рейтинга:
                        this.setMMR(this.getMMR() + 25 + opponentProfile.getRank() - this.getRank()); // Изменить MMR
                        if (this.getMMR() < 0) this.setMMR(0);
                        this.correctRank(); // Изменить ранг
                        bufferedWriter.write(this.getRank() + "/" + this.getMMR().toString());
                        bufferedWriter.newLine();
                        break;
                    case 3: //Коррекция побед:
                        this.setWin(this.getWin() + 1);
                        bufferedWriter.write(this.getWin() + "");
                        bufferedWriter.newLine();
                        break;
                    case 5: //Коррекция побед за выбранного героя:
//                        if (player.getCurrentHero().getClass().equals(HeroDevourer.class)) {
//                            this.setWinForDevourer(this.getWinForDevourer() + 1);
//                            bufferedWriter.debug(this.getWinForDevourer() + "");
//                            bufferedWriter.newLine();
//                        } else
//                            bufferedWriter.debug(lines.getProxy(i) + "\n");
//                        break;
                    case 6:
//                        if (player.getCurrentHero().getClass().equals(HeroLordVamp.class)) {
//                            this.setWinForLV(this.getWinForLV() + 1);
//                            bufferedWriter.debug(this.getWinForLV() + "");
//                            bufferedWriter.newLine();
//                        } else
//                            bufferedWriter.debug(lines.getProxy(i) + "\n");
//                        break;
                    case 7:
//                        if (player.getCurrentHero().getClass().equals(HeroOrcBasher.class)) {
//                            this.setWinForOrcBash(this.getWinForOrcBash() + 1);
//                            bufferedWriter.debug(this.getWinForOrcBash() + "");
//                            bufferedWriter.newLine();
//                        } else
//                            bufferedWriter.debug(lines.getProxy(i) + "\n");
//                        break;
                    default:
                        bufferedWriter.write(lines.get(i) + "\n");
                        break;
                }
            }
        } else { // Если поражение:
            for (int i = 0; i < 8; i++) {
                switch (i) {
                    case 2:
                        this.setMMR(this.getMMR() - 20 - this.getRank() + opponentProfile.getRank()); //Изменить MMR
                        if (this.getMMR() < 0) this.setMMR(0);
                        this.correctRank(); // Изменить ранг
                        bufferedWriter.write(this.getRank() + "/" + this.getMMR().toString());
                        bufferedWriter.newLine();
                        break;
                    case 4: //Коррекция поражений
                        this.setLose(this.getLose() + 1);
                        bufferedWriter.write(this.getLose() + "");
                        bufferedWriter.newLine();
                        break;
                    default:
                        bufferedWriter.write(lines.get(i) + "\n");
                        break;
                }
            }
        }
        bufferedWriter.close();
        bufferedReader.close();
    }


    private void correctRank() {
        if (this.getMMR() < 50) {
            this.setRank((byte) 1);
        } else if (this.getMMR() >= 50 && this.getMMR() < 100) {
            this.setRank((byte) 2);
        } else if (this.getMMR() >= 100 && this.getMMR() < 150) {
            this.setRank((byte) 3);
        } else if (this.getMMR() >= 150 && this.getMMR() < 200) {
            this.setRank((byte) 4);
        } else if (this.getMMR() >= 200 && this.getMMR() < 250) {
            this.setRank((byte) 5);
        } else if (this.getMMR() >= 250 && this.getMMR() < 300) {
            this.setRank((byte) 6);
        } else if (this.getMMR() >= 300 && this.getMMR() < 355) {
            this.setRank((byte) 7);
        } else if (this.getMMR() >= 355 && this.getMMR() < 410) {
            this.setRank((byte) 8);
        } else if (this.getMMR() >= 410 && this.getMMR() < 465) {
            this.setRank((byte) 9);
        } else if (this.getMMR() >= 465 && this.getMMR() < 520) {
            this.setRank((byte) 10);
        } else if (this.getMMR() >= 520 && this.getMMR() < 580) {
            this.setRank((byte) 11);
        } else if (this.getMMR() >= 580 && this.getMMR() < 640) {
            this.setRank((byte) 12);
        } else if (this.getMMR() >= 640 && this.getMMR() < 700) {
            this.setRank((byte) 13);
        } else if (this.getMMR() >= 700 && this.getMMR() < 765) {
            this.setRank((byte) 14);
        } else if (this.getMMR() >= 765 && this.getMMR() < 830) {
            this.setRank((byte) 15);
        } else if (this.getMMR() >= 830 && this.getMMR() < 900) {
            this.setRank((byte) 16);
        } else if (this.getMMR() >= 900 && this.getMMR() < 1000) {
            this.setRank((byte) 17);
        } else if (this.getMMR() >= 1000 && this.getMMR() < 1250) {
            this.setRank((byte) 18);
        } else if (this.getMMR() >= 1250 && this.getMMR() < 1500) {
            this.setRank((byte) 19);
        } else this.setRank((byte) 20);
    }


    //Getters and Setters:
    public String getName() {
        return name;
    }

    public void setName() {
        this.name = "";
    }

    private void setRank(Byte rank) {
        this.rank = rank;
    }

    public Integer getMMR() {
        return MMR;
    }

    private void setMMR(Integer MMR) {
        this.MMR = MMR;
    }

    public Integer getWin() {
        return win;
    }

    private void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLose() {
        return lose;
    }

    private void setLose(Integer lose) {
        this.lose = lose;
    }

    public Integer getWinForDevourer() {
        return winForDevourer;
    }

    private void setWinForDevourer(Integer winForDevourer) {
        this.winForDevourer = winForDevourer;
    }

    public Integer getWinForLV() {
        return winForLV;
    }

    private void setWinForLV(Integer winForLV) {
        this.winForLV = winForLV;
    }

    public Integer getWinForOrcBash() {
        return winForOrcBash;
    }

    private void setWinForOrcBash(Integer winForOrcBash) {
        this.winForOrcBash = winForOrcBash;
    }

    public BonusData getBonusData() {
        return bonusData;
    }

    public void setBonusData(BonusData bonusData) {
        this.bonusData = bonusData;
    }
}
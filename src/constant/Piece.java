package constant;

// 0: tướng đỏ,      1: tướng đen,
// 2: sỹ đỏ trái,    3: sỹ đỏ phải,    4: sỹ đen trái,    5: sỹ đen phải
// 6: tượng đỏ trái, 7: tượng đỏ phải, 8: tượng đen trái, 9: tượng đen phải
// 10: mã đỏ trái,   11: mã đỏ phải,   12: mã đen trái,   13: mã đen phải
// 14: xe đỏ trái,   15: xe đỏ phải,   16: xe đen trái,   17: xe đen phải
// 18: pháo đỏ trái, 19: pháo đỏ phải, 20: pháo đen trái, 21: pháo đen phải
// 22: tốt đỏ 0,     23: tốt đỏ 1,     24: tốt đỏ 2,      25: tốt đỏ 3,       26: tốt đỏ 4
// 27: tốt đen 0,    28: tốt đen 1,    29: tốt đen 2,     30: tốt đen 3,      31: tốt đen 4
public interface Piece {
    // size
    int CELL_SIZE = 70;
    int SIZE_PIECE = 69, RADIUS = 15;
    int _width_ = 18 * CELL_SIZE, _height_ = 11 * CELL_SIZE;
    int PADDING = 4 * CELL_SIZE;
    int LEFT = 0, RIGHT = 1;
    int BLACK = 1, RED = 0;
    int RED_GENERAL = 0;
    int BLACK_GENERAL = 1;
    int RED_LEFT_ADVISOR = 2;
    int RED_RIGHT_ADVISOR = 3;
    int BLACK_LEFT_ADVISOR = 4;
    int BLACK_RIGHT_ADVISOR = 5;
    int RED_LEFT_ELEPHANT = 6;
    int RED_RIGHT_ELEPHANT = 7;
    int BLACK_LEFT_ELEPHANT = 8;
    int BLACK_RIGHT_ELEPHANT = 9;
    int RED_LEFT_KNIGHT = 10;
    int RED_RIGHT_KNIGHT = 11;
    int BLACK_LEFT_KNIGHT = 12;
    int BLACK_RIGHT_KNIGHT = 13;
    int RED_LEFT_ROOK = 14;
    int RED_RIGHT_ROOK = 15;
    int BLACK_LEFT_ROOK = 16;
    int BLACK_RIGHT_ROOK = 17;
    int RED_LEFT_CANNON = 18;
    int RED_RIGHT_CANNON = 19;
    int BLACK_LEFT_CANNON = 20;
    int BLACK_RIGHT_CANNON = 21;
    int RED_PAWN_0 = 22;
    int RED_PAWN_1 = 23;
    int RED_PAWN_2 = 24;
    int RED_PAWN_3 = 25;
    int RED_PAWN_4 = 26;
    int BLACK_PAWN_0 = 27;
    int BLACK_PAWN_1 = 28;
    int BLACK_PAWN_2 = 29;
    int BLACK_PAWN_3 = 30;
    int BLACK_PAWN_4 = 31;
}
